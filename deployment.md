# 🚀 Kubernetes Deployment Guide for BeerCatalog API

This document outlines the steps required to build, configure, and deploy the BeerCatalog API to a local Kubernetes cluster using Minikube.

---

## 1️⃣ Start Minikube

Start the Minikube cluster using Docker as the driver:

```bash
minikube start --driver=docker
```

---

## 2️⃣ Build Docker Image

Build the Docker image for the application:

```bash
docker build -t api:1.0.0 .
```

To use the Docker daemon inside Minikube:

```bash
eval $(minikube docker-env)
```

---

## 3️⃣ Verify Docker Image

Ensure the image was successfully built:

```bash
docker images
```

---

## 4️⃣ Apply Kubernetes Secrets

Apply the secret configuration (e.g., database credentials):

```bash
kubectl apply -f ./secret.yaml
```

---

## 5️⃣ Deploy the Application

Apply the main deployment manifest:

```bash
kubectl apply -f ./applicationDeployment.yaml
```

---

## 6️⃣ Verify the Service

Check the service URL provided by Minikube:

```bash
minikube service api-service --url
```

---

## 7️⃣ Apply Ingress Configuration

Apply the ingress rule to expose the API via a custom domain:

```bash
kubectl apply -f ./ingress.yaml
```

---

## 8️⃣ Enable Minikube Tunnel

Expose the ingress controller to your host system:

```bash
minikube tunnel
```

> This may require sudo privileges.

---

## 9️⃣ Modify Hosts File

Add the following entry to your system's `/etc/hosts` file (or `C:\Windows\System32\drivers\etc\hosts` on Windows):

```plaintext
127.0.0.1 beercatalog.local
```

---

## 🔍 Access the API

You can now access the BeerCatalog API in your browser or via Postman at:

```
http://beercatalog.local/api/beers
```

---

## ✅ Final Notes

- Make sure the Docker image and all Kubernetes manifests are up to date.
- Swagger UI should be available at:  
  `http://beercatalog.local/swagger-ui.html`

