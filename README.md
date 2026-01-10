#  Career Key  Blockchain-Powered Degree Verification System

**Secure  Immutable  Decentralized**  
Production-grade backend for tamper-proof degree attestation using Ethereum, IPFS, and Spring Boot.

##  Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Architecture & Workflow](#architecture--workflow)
- [Quick Start](#quick-start)
- [Environment Configuration](#environment-configuration)
- [Smart Contracts](#smart-contracts)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Overview

Career Key is a **Final Year Project (FYP)** that provides a secure, blockchain-based platform to request, verify, and publicly validate academic credentials. It supports role-based workflows (Student, University, HEC Admin) and anchors verification records on the Ethereum blockchain while storing documents in IPFS for permanence and availability.

**Related Projects:**
- [Frontend Repository](https://github.com/Shahiskhan/career_key_frontend)
- [Live Demo](https://hec-nexus.netlify.app/)

---

## Key Features

-  **JWT-based Role-Based Access Control** (Student, University, HEC Admin)
-  **Multi-level Degree Request & Approval Workflow**
-  **4-Step HEC Attestation Process**: Digital Stamp  IPFS Upload  QR Generation  Blockchain Anchor
-  **Ethereum Smart Contracts** for immutable verification records
-  **IPFS Integration** for decentralized document storage
-  **QR Code Generation** for instant public verification
-  **Payment Gateway Integration** (1Link support)
-  **Docker & Docker Compose** for production-ready deployment
-  **AI Job Recommendations** (upcoming)

---

## Technology Stack

| Layer | Technology |
|-------|-----------|
| **Language & Framework** | Java 21, Spring Boot 3.x |
| **Build Tool** | Apache Maven |
| **Database** | PostgreSQL 12+ |
| **Blockchain** | Solidity, Web3j, Ethereum |
| **Storage** | IPFS (Pinata / Local Node) |
| **Security** | JWT, Spring Security |
| **Containerization** | Docker, Docker Compose |
| **API Documentation** | Swagger / OpenAPI |

---

## Architecture & Workflow

### 4-Step Attestation Process

1. **Student Submits Request**  Uploads degree documents with personal information.
2. **University Verification**  Institutional admin reviews and approves/rejects.
3. **HEC Attestation** (4 Steps):
   - Apply digital stamp on document
   - Upload stamped document to IPFS, obtain content hash
   - Generate and embed QR code in the document
   - Store IPFS hash + student identifier in smart contract
4. **Public Verification**  Scan QR code or query blockchain API for instant verification.

---

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 12+
- Docker & Docker Compose (recommended)
- Ethereum RPC Provider (Infura, Alchemy, or local node)
- IPFS Node or Pinning Service (Pinata, nft.storage, etc.)

### Local Setup

```bash
# Clone repository
git clone https://github.com/Shahiskhan/career-key-backend.git
cd career-key-backend

# Configure environment (see Environment Configuration section)
cp .env.example .env
# Edit .env with your values

# Build and run
mvn clean install
mvn spring-boot:run
```

**Application URL:** http://localhost:8080  
**Swagger UI:** http://localhost:8080/swagger-ui.html

### Docker Setup

```bash
docker compose up --build
```

---

## Environment Configuration

Create a `.env` file or set environment variables:

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/career_key
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

# JWT Security
JWT_SECRET=your_jwt_secret_key

# Blockchain
BLOCKCHAIN_RPC_URL=https://sepolia.infura.io/v3/YOUR_PROJECT_ID
CONTRACT_ADDRESS=0x...

# IPFS
IPFS_API_ENDPOINT=https://api.pinata.cloud
IPFS_API_KEY=your_api_key
IPFS_JWT_TOKEN=your_jwt_token

# Payment Gateway
PAYMENT_GATEWAY_KEY=your_1link_key
```

** Security:** Never commit secrets to version control. Use environment variables or secure secret management tools.

---

## Smart Contracts

**Location:** `solidity/StudentResults.sol`

### Deployment

1. Open [Remix IDE](https://remix.ethereum.org/)
2. Load `solidity/StudentResults.sol`
3. Compile with Solidity 0.8+
4. Deploy to your target network (Sepolia testnet recommended)
5. Copy deployed contract address and update `CONTRACT_ADDRESS` in environment config

Alternatively, use **Hardhat** or **Truffle**:

```bash
npx hardhat compile
npx hardhat deploy --network sepolia
```

---

## API Endpoints

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

### Degree Requests

```http
POST   /api/degree/request               Student submits request
PUT    /api/degree/verify/{id}           University approves/rejects
POST   /api/hec/attest/{id}              HEC executes attestation
GET    /api/degree/verify/{hash}         Public verification by IPFS hash
GET    /api/degree/{id}                  Retrieve degree details
```

**Full API documentation** available at `/swagger-ui.html` when Swagger is enabled.

---

## Testing

Run unit and integration tests:

```bash
mvn test
```

Tests use JUnit 5 and Mockito. Coverage includes:
- Authentication & authorization
- Degree request workflow
- Blockchain interactions
- IPFS integration

---

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m "Add feature description"`
4. Push to branch: `git push origin feature/your-feature`
5. Open a Pull Request

**Focus areas:**
- Expand test coverage (JUnit + Mockito)
- Improve error handling and logging
- Optimize smart contract gas usage
- Complete AI job-recommendation integration
- Add CI/CD pipelines

---

## License

MIT License  See [LICENSE](LICENSE) file for details.

---

## Contact

**Muhammad Shahis Khan**  
BS Computer Science | Full-Stack & Blockchain Developer

- **GitHub:** [github.com/Shahiskhan](https://github.com/Shahiskhan)
- **LinkedIn:** [linkedin.com/in/muhammad-shahis-khan](https://www.linkedin.com/in/muhammad-shahis-khan/)

Open to Junior Java / Spring Boot / Blockchain Developer opportunities in Pakistan 

---

**Let'"'"'s build secure, tamper-proof credentials for the future!**
