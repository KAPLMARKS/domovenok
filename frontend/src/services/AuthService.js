import http from "../http-common";

class AuthService {
    login(data) {
        return http.post("/auth/authenticate", data);
    }
}

export default new AuthService();