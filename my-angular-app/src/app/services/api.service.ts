export class ApiService {
    constructor(private http: HttpClient) {}

    getData(endpoint: string) {
        return this.http.get(endpoint);
    }

    postData(endpoint: string, data: any) {
        return this.http.post(endpoint, data);
    }

    // Additional methods for PUT, DELETE, etc. can be added here
}