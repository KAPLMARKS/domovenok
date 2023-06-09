import http from "../http-common";

class VerifyService {
    create(token) {
        console.log("/requisition/verify/" + token);
        return http.post("/requisition/verify/" + token);
    }
}

export default new VerifyService();