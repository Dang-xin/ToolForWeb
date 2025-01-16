import * as Constant from "../Constant";
export default class QueryInfo {
    id: number;
    businessName: string;
    queryName: string;
    query: Array<object>;
    status: number;
    result: Array<object>;

    constructor(id: number, businessName: string, queryName: string, query: Array<object>, status: number, result: Array<object>) {
        this.id = id;
        this.businessName = businessName;
        this.queryName = queryName;
        this.query = query;
        this.status = status;
        this.result = result;
    }

    public addNewQueryInfo(queryInfoList: Array<object>) {
        queryInfoList.push({
                [Constant.QueryInfoItem.ID]: this.id,
                [Constant.QueryInfoItem.BusinessName]: this.businessName,
                [Constant.QueryInfoItem.QueryName]: this.queryName,
                [Constant.QueryInfoItem.Query]: this.query,
                [Constant.QueryInfoItem.Status]: this.status,
                [Constant.QueryInfoItem.Result]: this.result,
        });
    }
}
