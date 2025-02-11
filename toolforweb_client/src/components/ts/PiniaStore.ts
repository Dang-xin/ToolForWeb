import { defineStore } from 'pinia'
import * as Message from "./ParseResponse";
import { toRaw } from "vue";
import * as Constant from "./Constant";
import * as Type from  "./Type";

let indexedDB: IDBFactory = (window as any).mozIndexedDB || (window as any).indexedDB || (window as any).webkitIndexedDB || (window as any).msIndexedDB;
let version: number = Number(getVersion());
checkIndexDB();

export const useDBQueryListStore = defineStore('DBQueryList', {
    // 静态数据
    state: () => {
        return {
            allDBQuery: []
        }
    },
    // 相当于计算属性(有数据缓存)
    getters: {
    },
    // actions即可以是同步函数也可以是异步函数
    actions: {
        uploadData(data: Array<Type.DBKey>) {
            const request = openIndexDB(indexedDB, Constant.DB.Name, Constant.StoreName.DBQueryList, version);

            request.then((db) => {
                if (db === null) {
                    return;
                }
                updateData(db, Constant.StoreName.DBQueryList, data);
            },(reason) => {
                Message.error("数据保存执行错误");
            })

        },
        reloadData(): Promise<Array<Type.QueryInfoItem>> {
            return new Promise((resolve, reject) => {
                const request = openIndexDB(indexedDB, Constant.DB.Name, Constant.StoreName.DBQueryList, version);

                request.then((db) => {
                    if (db === null) {
                        return;
                    }
                    const data = getData(db, Constant.StoreName.DBQueryList);
                    data.then((rows) => {
                        if (rows === null) {
                            Message.error("没有取得数据");
                            return;
                        }
                        resolve(rows);
                    },(reason) => {
                        reject([]);
                    })
                },(reason) => {
                    Message.error("数据读取错误");
                    reject([]);
                })
            })
        },
        delete() {
            deleteStore(Constant.StoreName.DBQueryList);
        }
    }
});

function checkIndexDB() {
 
    if (!indexedDB) {
        Message.error("该浏览器不支持开启IndexDB");
    }
    return;
}

function openIndexDB(indexedDB: IDBFactory, DBName: string, storeName: string, version: number) : Promise<IDBDatabase | null> {
    return new Promise((resolve, reject) => {

        let db: IDBDatabase;
        const request= indexedDB.open(DBName, version);
        request.onsuccess = (event) => {
            db = (event.target as IDBOpenDBRequest).result;
            Message.success("数据库打开成功");
            if (!db.objectStoreNames.contains(storeName)) {
               const request = indexedDB.open(DBName, version + 1);
               request.onupgradeneeded = (event) => {
                const db = (event.target as IDBRequest).result;
                if(!db.objectStoreNames.contains(storeName)) {
                    createStore(db, storeName);
                }
            }
            }
            resolve(db);
        }
        request.onerror = (event) => {
            Message.error("数据库打开失败");
            reject(null);
        }
        request.onupgradeneeded = (event) => {
            const db = (event.target as IDBRequest).result;
            if(!db.objectStoreNames.contains(storeName)) {
                createStore(db, storeName);
            }
        }
    })
}

function createStore(db: IDBDatabase, storeName: string) {
    let store = db.createObjectStore(storeName, {keyPath: Constant.DB.No});
    store.createIndex(Constant.DB.No, Constant.DB.No, {unique: true});
    store.transaction.oncomplete = (evt: any) => {
        Message.success(`数据库${storeName}创建成功,版本号${db.version}`);
    }
}

function deleteStore(storeName: string) {
    const request = indexedDB.open(Constant.DB.Name, version + 1);
    request.onsuccess = () => {
        Message.success("存储库打开成功");
    };
    
    request.onerror = () => {
        Message.error("存储库打开失败");
    };

    request.onupgradeneeded = (event) => {
        const db = (event.target as IDBOpenDBRequest).result;
    
        console.log(db.objectStoreNames.contains(storeName));
        // 确保对象存储存在，然后删除
        if (db.objectStoreNames.contains(storeName)) {
            Message.success("存储库删除开始");
            db.deleteObjectStore(storeName);
            const storage: Storage = window.localStorage;
            storage.setItem(Constant.DB.Version, String(db.version));
            Message.success(`存储库${storeName}删除成功,版本号${(db.version)}`);
        }
    }
}

function updateData(db: IDBDatabase, storeName: string, dataList: Array<Type.DBKey>) {
    let transaction = db.transaction([storeName], "readwrite");
    let store = transaction.objectStore(storeName);
    let result: IDBRequest<IDBValidKey> | IDBRequest<undefined>;
    dataList.forEach(data => {
        const status = data.Status;
        if (status === Constant.DBOperate.Insert) {
            data.Status = Constant.DBOperate.Select;
            result = store.add(toRaw(data));
        } else if (status === Constant.DBOperate.Delete) {
            result = store.delete(data.No);
        } else if (status === Constant.DBOperate.Update) {
            data.Status = Constant.DBOperate.Select;
            result = store.put(toRaw(data));
        } else {
            result = store.get(data.No);
        }
        result.onerror = () => {
            data.Status = status;
            Message.error("处理数据失败:" + JSON.stringify(data));
        }
        result.onsuccess = () => {
            Message.success(JSON.stringify(data) + "数据操作成功")
        }
    }); 
    transaction.oncomplete = () => {
        Message.success("事务提交成功");
    }
    transaction.onerror= () => {
        Message.error("事务提交失败");
    }
    db.close();
}

function getData(db: IDBDatabase, storeName: string): Promise<Array<Type.QueryInfoItem> | null> {
    return new Promise((resolve, reject) => {
        let data: Array<Type.QueryInfoItem> = [];
        let transaction = db.transaction([storeName], "readonly");
        let store = transaction.objectStore(storeName);
        let cursor = store.openCursor();
        cursor.onsuccess = (event) => {
            const read = (event.target as IDBRequest).result;
            if (read) {
                data.push(read.value);
                read.continue();
            }
            else {
                resolve(data);
            }
        }
        cursor.onerror = (event) => {
            reject(null);
        }
    })
}

function getVersion(): string {
    const storage: Storage = window.localStorage;
    if (storage.getItem(Constant.DB.Version) === null) {
        storage.setItem(Constant.DB.Version, String(1));
    }
    return storage.getItem(Constant.DB.Version)!;
}