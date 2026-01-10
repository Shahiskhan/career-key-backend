<div align="center">
  <h1> Career Key Backend</h1>
  <p><strong>Secure | Immutable | Decentralized</strong><br>
  Tamper-proof degree attestation using Ethereum blockchain, IPFS & modern Java stack </p>
</div>

##  Overview

Career Key is a full-stack **blockchain-based degree verification platform** designed to eliminate degree fraud in education systems (especially Pakistan via HEC integration).  

Built as Final Year Project (FYP), it enables:
- Students to request secure degree attestation  
- Universities to verify & approve documents  
- HEC admins to perform multi-step tamper-proof attestation  
- Anyone to instantly verify credentials via QR code or blockchain query  

**Frontend Repo:** [career_key_frontend](https://github.com/Shahiskhan/career_key_frontend)  
**Live Demo:** [https://hec-nexus.netlify.app/](https://hec-nexus.netlify.app/)

##  Key Features

-  **JWT Role-Based Authentication** (Student, University, HEC Admin)  
-  **Multi-Level Degree Request & Verification Workflow**  
-  **4-Step Secure HEC Attestation** (Digital Stamp  IPFS Upload  QR Generation  Blockchain Anchor)  
-  **Ethereum Smart Contracts** for immutable on-chain records  
-  **IPFS** for decentralized & tamper-proof document storage  
-  **QR Code** for quick public verification  
-  **Payment Integration** (1-Link gateway support)  
-  **Dockerized** for easy deployment  
-  **AI Job Recommendations** (Upcoming)

##  Technology Stack

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=java&logoColor=white" alt="Java 21" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-green?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" height="35"/>
  <br><br>
  <img src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/Solidity-363636?style=for-the-badge&logo=solidity&logoColor=white" alt="Solidity" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/Web3j-000000?style=for-the-badge&logo=ethereum&logoColor=white" alt="Web3j" height="35"/>
  <br><br>
  <img src="https://img.shields.io/badge/IPFS-65C2CB?style=for-the-badge&logo=ipfs&logoColor=white" alt="IPFS" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" height="35"/>
  &nbsp;
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT" height="35"/>
</div>

<p align="center"><em>Modern, secure & scalable tech for real-world blockchain applications!</em></p>

## How It Works  4-Step Magic Workflow

1.  **Student Request**  Submit degree docs  Backend creates request  
2.  **University Approval**  Review & forward to HEC  
3.  **HEC Attestation** (Core Process):  
   -  Digital Stamp  
   -  Upload to IPFS (get hash)  
   -  Generate & embed QR Code  
   -  Anchor on Blockchain (IPFS hash + Roll No. + CNIC)  
4.  **Public Verify**  Scan QR  Instant blockchain check!

##  Quick Start

### Prerequisites
- Java 21+  
- Maven 3.8+
- PostgreSQL 12+
- Docker (optional but recommended)  
- Ethereum RPC (Infura/Alchemy/Sepolia testnet)  
- IPFS (Pinata or local node)

### Setup Instructions
```bash
# Clone the repository
git clone https://github.com/Shahiskhan/career-key-backend.git
cd career-key-backend

# Configure environment variables
cp .env.example .env
# Edit .env with your database, JWT, RPC, and IPFS credentials

# Build and run
mvn clean install
mvn spring-boot:run
```

**Application Access:**
- Backend API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Docker Deployment
```bash
docker compose up --build
```

## Smart Contracts

**Location:** `solidity/StudentResults.sol`

Compile and deploy using:
- **Remix IDE:** https://remix.ethereum.org/ (copy-paste and deploy)
- **Hardhat:** `npx hardhat compile && npx hardhat deploy --network sepolia`
- **Truffle:** `truffle compile && truffle migrate --network sepolia`

Update deployed contract address in your `application.properties` or `.env`

##  API Endpoints

```http
POST   /api/auth/register           User Signup
POST   /api/auth/login              Get JWT Token
POST   /api/degree/request          Submit Degree Request
PUT    /api/degree/verify/{id}      University Approve/Reject
POST   /api/hec/attest/{id}         Execute Full HEC Attestation
GET    /api/degree/verify/{hash}    Public Degree Verification
```

**Full API documentation** available at `/swagger-ui.html` (when Swagger is enabled)

##  Testing

```bash
mvn test
```

Includes JUnit 5 + Mockito tests for:
- Authentication & Authorization
- Degree Request Workflow
- Blockchain Integration
- IPFS Operations

##  Contributing

We welcome contributions! Please:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit: `git commit -m "Add descriptive message"`
4. Push: `git push origin feature/your-feature`
5. Open a Pull Request

**Focus Areas:**
- Expand test coverage
- Improve error handling & logging
- Optimize smart contract gas usage
- Complete AI job-recommendation integration
- Add CI/CD pipelines

##  License

MIT License  Feel free to use, modify, and learn!

---

<div align="center">

##  Developer

**Muhammad Shahis Khan**  
*BS Computer Science | Full-Stack & Blockchain Developer*

###  Connect With Me

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Shahiskhan)
&nbsp;
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/muhammad-shahis-khan/)

###  Open To Opportunities

**Junior Java Developer | Spring Boot | Blockchain Developer**  
 Pakistan  
 Looking for exciting opportunities to build secure & scalable systems!

---

**Let's build the future of tamper-proof credentials together!** 

</div>
