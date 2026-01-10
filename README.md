# 🎓 Career Key Backend - Blockchain-Powered Degree Verification System

![Project Banner](https://img.freepik.com/free-vector/technology-linkedin-banner-template_742173-30387.jpg?semt=ais_hybrid&w=1200&q=80)  
<!-- Yeh ek modern tech banner hai – agar change karna ho to Canva se apna bana lo -->

**Secure | Immutable | Decentralized** – Tamper-proof degree attestation using Ethereum blockchain, IPFS, and modern Java stack.

Here are some real-world examples of blockchain-based document/degree verification systems in action:

<grok-card data-id="062ecb" data-type="image_card"  data-arg-size="LARGE" ></grok-card>



<grok-card data-id="5ed746" data-type="image_card"  data-arg-size="LARGE" ></grok-card>


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

Java + Spring Boot + Blockchain integration – powerful combo!

Here are some visuals showing Java/Web3j in blockchain development:

<grok-card data-id="7e63db" data-type="image_card"  data-arg-size="LARGE" ></grok-card>


## How It Works – Secure 4-Step Attestation Workflow

Here are examples of QR codes on certificates for blockchain verification:

<grok-card data-id="6824eb" data-type="image_card"  data-arg-size="LARGE" ></grok-card>



<grok-card data-id="977bea" data-type="image_card"  data-arg-size="LARGE" ></grok-card>


1. **Student Requests Degree** 📝 → Backend creates entry in DB.  
2. **University Verification** ✅ → Uni approves & forwards to HEC.  
3. **HEC 4-Step Attestation** (Core Magic!):  
   - 🔖 Digital Stamp on Document  
   - 📤 Upload to IPFS → Get Hash  
   - 🖨️ Generate & Embed QR Code  
   - ⛓️ Anchor on Blockchain (IPFS Hash + Roll No. + CNIC)  
4. **Public Verification** 🔎 → Scan QR → Instant blockchain check!

Architecture examples from similar systems:

<grok-card data-id="c55b87" data-type="image_card"  data-arg-size="LARGE" ></grok-card>


## 🚀 Installation & Quick Start

### Prerequisites
- Java 21+  
- Maven  
- PostgreSQL  
- Docker (recommended)  
- Ethereum RPC (Infura/Alchemy)  
- IPFS access

### Local Setup
```bash
git clone https://github.com/Shahiskhan/career-key-backend.git
cd career-key-backend
```
## 🚀 Installation & Quick Start (continued)

**App runs at:** `http://localhost:8080`  
**Swagger Docs (if enabled):** `/swagger-ui.html`

### Smart Contracts
Compile & deploy Solidity files from `solidity/` folder using Remix/Truffle, then update contract address in config.

## 📚 API Endpoints (Key Ones)

```http
POST   /api/auth/register          → Signup
POST   /api/auth/login             → Get JWT
POST   /api/degree/request         → Submit degree
PUT    /api/degree/verify/{id}     → University approve
POST   /api/hec/attest/{id}        → Full 4-step HEC process
GET    /api/degree/verify/{hash}   → Public verification
```
🤝 Contributing
Pull requests are welcome!
Focus on:

Tests (JUnit)
Bug fixes
New features (e.g., full AI integration)

📄 License
MIT License – Feel free to use & learn!

Developed with ❤️ by Muhammad Shahis Khan
BS Computer Science | Full-Stack & Blockchain Developer
GitHub | LinkedIn
Open to Junior Java / Spring Boot / Blockchain Developer opportunities in Pakistan! 🚀
Let's connect & build the future of secure credentials!
