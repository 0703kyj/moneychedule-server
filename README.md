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
- birth
- code
- uuid
- phoneNumber
- followCount

### Follow (BaseEntity)
- follow_id
- user_id
- following_id

### ScheduledUser
- user_id
- schedule_id

### Schedule (DeletableBaseEntity)
- schedule_id
- scheduleDate
- Period
  - startTime
  - endTime
- color
- memo

### PaymentUser
- user_id
- payment_id

### Payment (DeletableBaseEntity)
- payment_id
- paymentDate
- type
- amount
- memo
