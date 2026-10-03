import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// En local (npm run dev sur ta machine) : le backend tourne sur localhost:8080.
// Dans docker-compose : le backend est un autre conteneur, joignable par son nom de service "app".
const backendUrl = process.env.VITE_BACKEND_URL || 'http://localhost:8080'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true, // écoute sur 0.0.0.0 : nécessaire pour être accessible depuis l'extérieur du conteneur
    watch: {
      // Docker Desktop (Windows/Mac) ne propage pas toujours les évènements de
      // changement de fichier à travers un volume monté : on force le polling.
      usePolling: true,
    },
    proxy: {
      '/chat': backendUrl,
      '/chien': backendUrl,
      '/course': backendUrl,
      '/classement': backendUrl,
    },
  },
})
