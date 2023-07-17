FROM node:18.16-alpine

WORKDIR /eps-client-api

COPY . .

RUN npm install

RUN npm run build

CMD ["npm", "start"]

EXPOSE 3000