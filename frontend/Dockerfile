# Usa a imagem oficial do Node.js 18
FROM node:18.17.0

# Define o diretório de trabalho
WORKDIR /app

# Copia package.json e instala as dependências
COPY package*.json ./
RUN npm install -g npm@9.6.7 && npm install

# Copia o restante do código
COPY . .

# Expor a porta do Angular
EXPOSE 4200

# Comando para rodar o Angular com hot reload
CMD ["npm", "run", "start"]
