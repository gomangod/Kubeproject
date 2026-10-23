FROM nginx:alpine
WORKDIR /app
COPY target/index.html index.html
EXPOSE 80
CMD ["-"]