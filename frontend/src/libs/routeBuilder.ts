import { Routes } from "@/router/routes"

export const mergeLessonFormData = (forms: string[]) => forms.join(',')
export const separateLessonFromData = (forms: string) => forms.split(',')
export const buildSearchUrl = (
    subject: string, 
    forms: string[],
    base = Routes.TUTOR_SEARCH, ) => `${base}/${subject}/${mergeLessonFormData(forms)}`

export const urlParamsBuilder = (base: string, params: Record<string, string>) => {
    const paramsList = []
    for (const param in params){
        paramsList.push(`${param}=${params[param]}`)
    }
    return `${base}?${paramsList.join('&')}`
}