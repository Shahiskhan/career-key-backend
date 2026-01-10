# 🎓 Career Key Backend - Blockchain-Powered Degree Verification System

**Secure | Immutable | Decentralized** – Tamper-proof degree attestation using Ethereum blockchain, IPFS, and modern Java stack.

## 🌟 Overview

Career Key is a full-stack **blockchain-based degree verification platform** designed to eliminate degree fraud. Built as a Final Year Project (FYP), it enables:

- Students to request degree attestation  
- Universities to verify & approve  
- HEC (Higher Education Commission) to perform multi-step secure attestation  
- Anyone to instantly verify credentials via QR code or blockchain query

This backend powers the entire logic with **enterprise-grade Java/Spring Boot** and **Ethereum smart contracts**.

**Frontend Repo:** [career_key_frontend](https://github.com/Shahiskhan/career_key_frontend)  
**Live Demo (Frontend):** [https://hec-nexus.netlify.app/](https://hec-nexus.netlify.app/)

## 🔥 Key Features

- 🔐 **JWT Role-Based Authentication** (Student, University, HEC Admin)  
- 📄 **Degree Request & Multi-Level Verification Workflow**  
- 🛡️ **4-Step HEC Attestation Process** (Digital Stamp → IPFS → QR → Blockchain Anchor)  
- ⛓️ **Ethereum Smart Contracts** for immutable records  
- 📤 **IPFS Decentralized Storage** for documents  
- 🔍 **QR Code Generation** for instant verification  
- 💳 **Payment Gateway Integration** (1-Link support)  
- 🚀 **Dockerized & Ready for Cloud Deployment**  
- 🤖 **AI Job Recommendations** (Upcoming feature)

## 🛠️ Technology Stack

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=java&logoColor=white" alt="Java 21" height="30"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-green?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" height="30"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" height="30"/>
  <img src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" height="30"/>
  <img src="https://img.shields.io/badge/Solidity-363636?style=for-the-badge&logo=solidity&logoColor=white" alt="Solidity" height="30"/>
  <img src="https://img.shields.io/badge/Web3j-000000?style=for-the-badge&logo=ethereum&logoColor=white" alt="Web3j" height="30"/>
  <img src="https://img.shields.io/badge/IPFS-65C2CB?style=for-the-badge&logo=ipfs&logoColor=white" alt="IPFS" height="30"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" height="30"/>
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT" height="30"/>
</div>

Modern, scalable, and recruiter-loved tech stack!

## How It Works – Secure 4-Step Attestation Workflow

1. **Student Requests Degree** 📝  
   Student submits request with documents → Backend creates entry in DB.

2. **University Verification** ✅  
   Uni admin reviews & approves → Request forwarded to HEC.

3. **HEC 4-Step Attestation** (The Core Magic!):  
   - 🔖 Digital Stamp on Document  
   - 📤 Upload Stamped Doc to IPFS → Get Hash  
   - 🖨️ Generate & Embed QR Code on Document  
   - ⛓️ Anchor on Blockchain: Store (IPFS Hash + Roll No. + CNIC) in Smart Contract

4. **Public Verification** 🔎  
   Scan QR or query API/blockchain → Instant “Valid/Invalid” result!

## 🚀 Installation & Quick Start

### Prerequisites
- Java 21+  
- Maven 3.8+  
- PostgreSQL 15+  
- Docker (recommended)  
- Ethereum RPC (Infura/Alchemy for testnet)  
- IPFS access (Pinata or local node)

### Local Setup
```bash
git clone https://github.com/Shahiskhan/career-key-backend.git
cd career-key-backend

# 2. Setup .env (create from .env.example or manually)
# Example keys: SPRING_DATASOURCE_URL, JWT_SECRET, BLOCKCHAIN_RPC_URL etc.

# 3. Build & Run
mvn clean install
mvn spring-boot:run
```
App runs at: http://localhost:8080
Swagger Docs (if enabled): http://localhost:8080/swagger-ui.html
Smart Contracts

Compile & deploy Solidity files from solidity/ folder using Remix IDE or Truffle/Hardhat, then update the deployed contract address in your application config.
📚 API Endpoints

httpPOST   /api/auth/register          → User Signup
POST   /api/auth/login             → Get JWT Token
POST   /api/degree/request         → Submit Degree Request
PUT    /api/degree/verify/{id}     → University Approve/Reject
POST   /api/hec/attest/{id}        → Full 4-step HEC Attestation Process
GET    /api/degree/verify/{hash}   → Public Degree Verification

🤝 Contributing
Pull requests are welcome!
Focus areas:
Adding Tests (JUnit + Mockito)
Bug fixes & improvements
New features (e.g., full AI integration for job recommendations)

📄 License
MIT License – Feel free to use, modify, and learn!

Developed with ❤️ by Muhammad Shahis Khan
BS Computer Science | Full-Stack & Blockchain Developer
GitHub | LinkedIn
Open to Junior Java / Spring Boot / Blockchain Developer opportunities in Pakistan! 🚀
Let's connect and build the future of secure credentials!
