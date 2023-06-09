import http from "../http-common";

class RequisitionService {
  create(data) {
    return http.post("/requisition", data);
  }
}

export default new RequisitionService();