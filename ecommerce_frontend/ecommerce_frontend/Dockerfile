FROM node:22

WORKDIR /app

COPY ecommerce_frontend/package*.json ./

RUN npm install

COPY ecommerce_frontend .

EXPOSE 5173

CMD ["npm","run","dev","--","--host"]