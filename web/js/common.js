
 function OnloadDBConnectionInfo(callback) {
    sessionStorage.clear();
    for (let key in localStorage.getItem("DBConnectionName")) {
        sessionStorage.setItem(key, localStorage.getItem(key));
    }
    callback();
 }

 function a(callback) {
     callback(1,2)
 }


