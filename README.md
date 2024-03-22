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
- group_id
- name
- email
- password
- birth
- phoneNumber
- locale
- activated

### Follow (DeletableBaseEntity)
- follow_id
- member_id
- following_id
- followingName
- followStatus
- anniversary

### Team (BaseEntity)
- team_id
- inviteCode
- memberCount
- anniversaryDate
- anniversary

### ScheduledMember
- member_id
- schedule_id

### Schedule (DeletableBaseEntity)
- schedule_id
- scheduleDate
- Period
  - startTime
  - endTime
- memo
- color_id

### Color (Schedule)
- color_id
- name
- color

### PaymentMember
- member_id
- payment_id

### Payment (DeletableBaseEntity)
- payment_id
- paymentDate
- dtype
- memo
- amount

### Withdraw (Payment)
- withdrawType

### Deposit (Payment)
- depositType
