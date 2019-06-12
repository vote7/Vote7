import { request } from "./Api";
import { sortedBy } from "../shared/utils";

export const GroupApi = {
  getAll: async token => {
    const groups = await request({
      method: "GET",
      url: "/groups",
      params: { token },
    });
    return sortedBy(groups, "name");
  },

  get: async (token, groupId) => {
    const group = await request({
      method: "GET",
      url: `/groups/${groupId}`,
      params: { token },
    });
    // Workaround: backend returns users in random order
    group.users = sortedBy(group.users, "name");
    return group;
  },

  create: (token, { name, description }) =>
    request({
      method: "POST",
      url: "/groups",
      params: { token },
      body: { name, description },
    }),

  delete: (token, groupId) =>
    request({
      method: "DELETE",
      url: `/groups/${groupId}`,
      params: { token },
    }),

  addUser: (token, groupId, userId) =>
    request({
      method: "PATCH",
      url: `/groups/${groupId}/add/${userId}`,
      params: { token },
    }),

  removeUser: (token, groupId, userId) =>
    request({
      method: "PATCH",
      url: `/groups/${groupId}/remove/${userId}`,
      params: { token },
    }),
  
  getUserGroups: async (token, userId) => {
    const groups = await request({
      method: "GET",
      url: `/groups/admin/${userId}`,
      params: { token },
    });
    return sortedBy(groups, "name");
  },
};
