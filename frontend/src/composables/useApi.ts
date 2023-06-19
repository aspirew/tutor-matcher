import { useFetch } from "@vueuse/core";
import type { Endpoints } from "./endpoints";
import { urlParamsBuilder } from "@/libs/routeBuilder";


export const useGet = (endpoint: Endpoints, params?: Record<string, string>) => {
    const url = params ? urlParamsBuilder(`${import.meta.env.VITE_SERVER_ENDPOINT}${endpoint}`, params) : `${import.meta.env.VITE_SERVER_ENDPOINT}${endpoint}`
    return useFetch(url).get().json()
}
export const usePost = (endpoint: Endpoints) => useFetch(`${import.meta.env.VITE_SERVER_ENDPOINT}${endpoint}`).post().json()