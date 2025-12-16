Entities and Repositories — Project Reference

یہ ڈاکیومنٹ `com.crear.entities` میں موجود تمام Entity classes، `com.crear.enums` میں موجود Enums، اور `com.crear.repositories` میں موجود Repository interfaces کا خلاصہ دیتی ہے۔

نوٹ: یہ خلاصہ سورس کوڈ سے لیا گیا ہے — فیلڈز، تعلقات (JPA annotations)، ڈیفالٹ ویلیوز، اور کسی بھی خاص ریپوزیٹری method کا ذکر شامل ہے۔

---

**1. عمومی ہدایات**
- فائل: `src/main/java/com/crear/entities` اور `src/main/java/com/crear/enums` اور `src/main/java/com/crear/repositories`
- زیادہ تر کلاسز Lombok (`@Getter`, `@Setter`, `@Builder`, وغیرہ) استعمال کرتی ہیں، لہٰذا explicit getter/setter methods سورس میں نہیں ملیں گی مگر موجودہ فیلڈز کے لیے آٹومیٹکلی جنریٹ ہو جاتے ہیں۔

---

**2. Entities (تفصیل)**

- **`BaseEntity`** (abstract)
  - پیکج: `com.crear.entities`
  - فیلڈز:
    - `Long id` — `@Id`, `@GeneratedValue` (IDENTITY)
    - `Instant createdOn` — default `Instant.now()` (nullable=false, updatable=false)
    - `Instant updatedOn` — default `Instant.now()` (nullable=false)
    - `Long createdByUserId`, `Long updatedByUserId`
    - `Boolean deleted` — soft-delete flag
    - `Long deletedBy`, `Instant deletedOn`
  - میتھڈز:
    - `@PreUpdate onUpdate()` — updates `updatedOn` to `Instant.now()` قبل از update

- **`User`**
  - فیلڈز:
    - `String email` (unique, not null)
    - `String passwordHash` (not null)
    - `Role role` (`@Enumerated(EnumType.STRING)`)
    - `String phone`, `String displayName`
    - One-to-one mapped profiles: `Student student`, `University university`, `Hec hec`
  - نوٹ: `UserRepository` میں `findByEmail(String)` موجود ہے۔

- **`Student`**
  - فیلڈز:
    - `User user` (one-to-one, unique)
    - `String cnic` (unique)
    - `String fullName`, `LocalDate dateOfBirth`, `String gender`, `String contactNumber`, `String address`
    - `Boolean graduated`
    - `String skillsJson`, `certificationsJson`, `internshipsJson` (text columns)
    - `University university` (ManyToOne)
    - Relations: `Set<DegreeRequest> degreeRequests`, `Set<Degree> degrees`

- **`University`**
  - فیلڈز:
    - `User user` (one-to-one)
    - `String name`, `String city`, `String charterNumber` (unique), `String issuingAuthority`
    - `Boolean hecRecognized`
    - `Hec hec` (ManyToOne)
    - Relations: `Set<Student> students`, `Set<DegreeRequest> degreeRequests`, `Set<Degree> degrees`

- **`Hec`**
  - فیلڈز:
    - `User user` (one-to-one)
    - `String hecCode` (unique)
    - `String name`, `String headOfficeAddress`
    - `String digitalSealCertPath` (path to seal cert)
    - Relations: `Set<University> universities`, `Set<Degree> stampedDegrees`

- **`DegreeRequest`**
  - فیلڈز:
    - `Student student` (ManyToOne)
    - `University university` (ManyToOne)
    - `String program`, `String rollNumber`, `Integer passingYear`, `Double cgpa`
    - `Instant requestDate` (default = now)
    - `RequestStatus status` (`@Enumerated`, default = `PENDING`)
    - `String remarks` (text)
    - One-to-one: `Degree degree`, `Payment payment` (mappedBy on other side)

- **`Degree`**
  - فیلڈز:
    - `DegreeRequest degreeRequest` (one-to-one)
    - `University university` (ManyToOne)
    - `Student student` (ManyToOne)
    - `String program`, `Double cgpa`, `Integer passingYear`, `Instant issueDate`
    - `String verificationHash`, `String ipfsCid`, `String qrText`
    - `DegreeStatus status` (ENUM, default = `UNIVERSITY_VERIFIED`)
    - One-to-one relations: `Stamp stamp`, `IpfsFile ipfsFile`, `QRCode qrCode`
    - One-to-many: `Set<BlockchainTx> blockchainTxs`
    - `Hec hecOfficer` (ManyToOne, optional)

- **`Stamp`**
  - فیلڈز:
    - `Degree degree` (one-to-one, unique)
    - `String stampImagePath`
    - `Long stampingOfficerUserId`
    - `Instant stampDate` (default = now)
    - `String comments` (text)

- **`IpfsFile`**
  - فیلڈز:
    - `Degree degree` (one-to-one, unique)
    - `String cid`, `String fileName`, `String mimeType`
    - `Instant uploadedOn` (default = now)
    - `Long uploadedByUserId`

- **`QRCode`**
  - فیلڈز:
    - `Degree degree` (one-to-one, unique)
    - `String qrPayloadJson` (text, e.g., contains txHash & cid)
    - `Instant generatedOn` (default = now), `Instant expiresOn`

- **`BlockchainTx`**
  - فیلڈز:
    - `Degree degree` (ManyToOne)
    - `String txHash`, `Long blockNumber`, `String contractAddress`, `Long gasUsed`, `String senderAddress`
    - `AnchoringStatus anchoringStatus` (ENUM, default = `PENDING`)
    - `Instant confirmationTime`

- **`Payment`**
  - فیلڈز:
    - `DegreeRequest degreeRequest` (one-to-one, unique)
    - `Double amount`, `String currency`, `String transactionRef`
    - `PaymentStatus paymentStatus` (ENUM, default = `INIT`)
    - `Instant paidOn`, `String paymentGateway`

- **`Skill`**
  - فیلڈز:
    - `String name` (unique)
    - `Set<StudentSkill> studentSkills`

- **`StudentSkill`**
  - فیلڈز:
    - `Student student` (ManyToOne)
    - `Skill skill` (ManyToOne)
    - `String proficiencyLevel` (string), `Boolean endorsed`

- **`Recommendation`**
  - فیلڈز:
    - `Student student` (ManyToOne)
    - `JobPost jobPost` (ManyToOne)
    - `Double matchScore`, `String explanationText` (text)
    - `Boolean isBookmarked`, `Boolean isApplied` (defaults = false)
    - `Instant createdOn` (default = now)

- **`JobPost`**
  - فیلڈز:
    - `String title`, `String company`, `String location`
    - `String descriptionSnippet` (text)
    - `String postUrl`, `Instant postDate`, `Instant expiryDate`
    - `String sourceType` (e.g., LINKEDIN)
    - `Boolean expired` (default = false)

- **`AuditLog`**
  - فیلڈز:
    - `Long actorUserId`, `String entityType`, `Long entityId`
    - `String oldValue` (text), `String newValue` (text)
    - `String action` (e.g., SUBMIT, APPROVE)
    - `Instant actionTime` (default = now)

---

**3. Enums**

- `Role` — `STUDENT`, `UNIVERSITY`, `HEC`, `ADMIN`
- `RequestStatus` — `PENDING`, `VERIFIED`, `REJECTED`
- `PaymentStatus` — `INIT`, `PAID`, `FAILED`
- `DegreeStatus` — `UNIVERSITY_VERIFIED`, `HEC_STAMPED`, `BLOCKCHAIN_ANCHORED`
- `AnchoringStatus` — `PENDING`, `CONFIRMED`, `FAILED`

---

**4. Repositories (interfaces and any custom methods)**

- `JobPostRepository extends JpaRepository<JobPost, Long>`
- `SkillRepository extends JpaRepository<Skill, Long>`
- `RecommendationRepository extends JpaRepository<Recommendation, Long>`
- `QRCodeRepository extends JpaRepository<QRCode, Long>`
- `PaymentRepository extends JpaRepository<Payment, Long>`
- `IpfsFileRepository extends JpaRepository<IpfsFile, Long>`
- `HecRepository extends JpaRepository<Hec, Long>`
- `DegreeRequestRepository extends JpaRepository<DegreeRequest, Long>`
- `DegreeRepository extends JpaRepository<Degree, Long>`
- `BlockchainTxRepository extends JpaRepository<BlockchainTx, Long>`
- `AuditLogRepository extends JpaRepository<AuditLog, Long>`
- `UserRepository extends JpaRepository<User, Long>`
  - Custom: `Optional<User> findByEmail(String email)`
- `UniversityRepository extends JpaRepository<University, Long>`
- `StudentSkillRepository extends JpaRepository<StudentSkill, Long>`
- `StudentRepository extends JpaRepository<Student, Long>`
- `StampRepository extends JpaRepository<Stamp, Long>`

نوٹ: ان میں سے اکثر repository انٹرفیسز Spring Data JPA کے `JpaRepository` کو بڑھاتے ہیں؛ custom query methods صرف `UserRepository` میں ایک ہے۔ مزید custom queries عام طور پر `@Query` یا Query Methods کے ذریعے بنائے جاتے ہیں (پر موجود سورس میں مزید custom methods نہیں ملے)۔

---

**5. Quick Usage Tips (آپ کے اگلے کام کے لیے)**
- Entities relation mapping دیکھ کر service level میں transactional boundaries بنائیں — مثال: degree creation، IPFS اپ لوڈ، اور blockchain anchoring کو atomic steps میں divide کریں۔
- DTOs: project میں `dtos` موجود ہیں (مثلاً `StandardizedJob.java`) — API layer کے لیے DTOs بنائیں/استعمال کریں تاکہ entities کو براہِ راست expose نہ کریں۔
- Repositories: `UserRepository.findByEmail()` authentication اور user lookup کے لیے استعمال ہوگا۔ اگر آپ کو اور queries چاہیے تو method name conventions (e.g., `findByUniversityAndStatus`) استعمال کریں یا `@Query` لگائیں۔
- Default values: کئی فیلڈز `@Builder.Default` کے ساتھ آتے ہیں؛ جب service layer میں `new` یا repository.save() کرتے ہیں تو ان defaults کا خیال رکھیں۔

---

**6. فائل لوکیشنز (Quick reference)**
- Entities: `src/main/java/com/crear/entities/*`
- Enums: `src/main/java/com/crear/enums/*`
- Repositories: `src/main/java/com/crear/repositories/*`

---

اگر آپ چاہیں تو میں یہی ڈاکیومنٹ PDF میں کنورٹ کر کے `docs/Entities_and_Repositories.pdf` کے طور پر رکھ دوں گا — آپ بتائیں اگر میں فوراً PDF بنا دوں تو میں conversion کر کے PDF فائل create کرنے کے steps چلاؤں گا۔

خلاصہ: میں نے تمام entities، enums اور repositories کا مکمل خلاصہ یہیں شامل کیا ہے۔ اگر آپ چاہیں تو میں اس میں: (a) UML کلاس ڈایاگرام، (b) ERD (database relationships)، یا (c) service-layer sketch بھی شامل کر دوں۔
