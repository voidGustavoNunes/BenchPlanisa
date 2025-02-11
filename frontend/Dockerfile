# Usa a imagem oficial do Node.js 18
FROM node:18.17.0

# Define o diretório de trabalho
WORKDIR /app

# Copia o package.json e instala as dependências
COPY package*.json ./
RUN npm install -g npm@9.6.7 && npm install

# Copia o restante do código
COPY . .

# Compila o projeto Angular
RUN npm run build --prod

# Usa um servidor Nginx para rodar o frontend
FROM nginx:alpine
COPY --from=0 /app/dist/* /usr/share/nginx/html

# Expõe a porta 80
EXPOSE 80

# Inicia o Nginx
CMD ["nginx", "-g", "daemon off;"]
