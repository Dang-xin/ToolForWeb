import { defineStore } from 'pinia'
import * as message from "./ParseResponse";
import { toRaw } from "vue";
import * as Constant from "./Constant";

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
        uploadData(data: object[]) {
            const indexedDB = checkIndexDB();
            const request = openIndexDB(indexedDB, Constant.DB.Name, Constant.StoreName.DBQueryList, 1);

            request.then((db) => {
                updateData(db, Constant.StoreName.DBQueryList, data);
            },(reason) => {
                message.error("数据保存执行错误");
            })

        },
        reloadData() {
            return new Promise((resolve, reject) => {
                const indexedDB = checkIndexDB();
                const request = openIndexDB(indexedDB, Constant.DB.Name, Constant.StoreName.DBQueryList, 1);
                let result: Array<object> = [];

                request.then((db) => {
                    const data = getData(db, Constant.StoreName.DBQueryList);
                    data.then((rows) => {
                        resolve(rows);
                    },(reason) => {
                        reject([]);
                    })
                },(reason) => {
                    message.error("数据读取错误");
                    reject([]);
                })

            })
        }

    }
});

function checkIndexDB() {
    const indexedDB = (window as any).mozIndexedDB || (window as any).indexedDB || (window as any).webkitIndexedDB || (window as any).msIndexedDB;
    if (!indexedDB) {
        message.error("该浏览器不支持开启IndexDB");
    }
    return indexedDB;
}

function openIndexDB(indexedDB: IDBFactory, DBName: string, storeName: string, version: number) : Promise<IDBDatabase | null> {
    return new Promise((resolve, reject) => {

        let db: IDBDatabase;
        const request= indexedDB.open(DBName, version);
        request.onsuccess = (event) => {
            db = (event.target as IDBOpenDBRequest).result;
            message.success("数据库打开成功");
            resolve(db);
        }
        request.onerror = (event) => {
            message.error("数据库打开失败");
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
    // 为该数据库创建一个对象仓库，创建一个名为“DBQueryList”的仓库，并指定id作为键路径(keyPath)
    let store = db.createObjectStore(storeName, {keyPath: Constant.DB.ID});
    store.createIndex(Constant.DB.ID, Constant.DB.ID, {unique: true});
    store.transaction.oncomplete = (evt: any) => {
        message.success("数据库创建成功");
    }
}

function updateData(db: IDBDatabase, storeName: string, dataList: object[]) {
    let transaction = db.transaction([storeName], "readwrite");
    let store = transaction.objectStore(storeName);
    let result: IDBRequest<IDBValidKey>;
    for (let data of dataList) {
        const status = data[Constant.DB.Status];
        if (status === Constant.DBOperate.Insert) {
            data[Constant.DB.Status] = Constant.DBOperate.Select;
            result = store.add(toRaw(data));
        } else if (status === Constant.DBOperate.Delete) {
            result = store.delete(data[Constant.DB.ID]);
        } else if (status === Constant.DBOperate.Update) {
            data[Constant.DB.Status] = Constant.DBOperate.Select;
            result = store.put(toRaw(data));
        } else if (status === Constant.DBOperate.Select) {
            result = store.get(data[Constant.DB.ID]);
        }
        result.onerror = () => {
            data[Constant.DB.Status] = status;
            message.error("处理数据失败:" + JSON.stringify(data));
        }
        result.onsuccess = () => {
            message.success(JSON.stringify(data) + "数据操作成功")
        }
    }
    transaction.oncomplete = () => {
        message.success("事务提交成功");
    }
    transaction.onerror= () => {
        message.error("事务提交失败");
    }
    db.close();
}

function getData(db: IDBDatabase, storeName: string): Promise<Array<object> | null> {
    return new Promise((resolve, reject) => {
        let data: Array<object> = [];
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

