# MoneyChedule

# Domain
## Base

### BaseEntity
- created_at
- updated_at    

### DeletableBaseEntity
- created_at
- updated_at
- deleted_at

## Model
### Member (DeletableBaseEntity)
- member_id
- team_id
- platform
- platform_id
- email
- password
- locale
- activated
- name
- birth
- phoneNumber

### Team (BaseEntity)
- team_id
- inviteCode
- memberCount
- anniversaryDate
- anniversary

### Attendee
- member_id
- schedule_id

### Schedule (DeletableBaseEntity)
- schedule_id
- EventDate
  - date
  - time
- memo
- label_id
- repeatType

### Label (Schedule)
- label_id
- team_id
- name
- value

### Payment (DeletableBaseEntity)
- payment_id
- member_id
- paymentDate
- dtype
- memo
- amount

### Withdraw (Payment)
- withdrawType

### Deposit (Payment)
- depositType
