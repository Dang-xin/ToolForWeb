import axios from "axios";

const request = axios.create({
  baseURL: "http://localhost:8080", //这里配置的是后端服务提供的接口
  headers: {"Content-Type": "application/x-www-form-urlencoded"},
  withCredentials: true, // 允许携带凭据
  timeout: 0,
});
export default request;