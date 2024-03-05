# MoneyChedule

# Domain
## Base
### BaseTime
- created_at
- updated_at

### BaseEntity
- created_at
- updated_at
- created_by
- updated_by    

### DeletableBaseEntity
- created_at
- updated_at
- deleted_at
- created_by
- updated_by
- deleted_by

## Model
### User (BaseTime)
- user_id
- name
- email
- password
- appPassword
- birth
- code
- phoneNumber
- followCount
- locale

### Follow (BaseEntity)
- follow_id
- user_id
- following_id
- following_name
- anniversary

### ScheduledUser
- user_id
- schedule_id

### Schedule (DeletableBaseEntity)
- schedule_id
- scheduleDate
- Period
  - startTime
  - endTime
- memo
- color_id

### Color
- color_id
- name
- color

### PaymentUser
- user_id
- payment_id

### Payment (DeletableBaseEntity)
- payment_id
- paymentDate
- dtype
- memo
- amount

### Withdraw (Payment)
- payment_id
- withdrawType

### Deposit (Payment)
- payment_id
- depositType
