/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_SERVER_ENDPOINT: string
    readonly VITE_OAUTH_ENABLED: string
    readonly VITE_GOOGLE_OAUTH_CLIENT_ID: string
    readonly VITE_GOOGLE_OAUTH_CLIENT_SECRET: string
    readonly VITE_GOOGLE_OAUTH_REDIRECT: string

  }
  
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }