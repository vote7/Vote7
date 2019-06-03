import { request } from "./Api";
import { uniqueBy } from "../shared/utils";

export const UserApi = {
  getAll: async token => {
    const users = await request({
      method: "GET",
      url: "/users",
      params: { token },
    });

    // Workaround: backend returns duplicated users
    return uniqueBy(users, user => user.id);
  },
};
