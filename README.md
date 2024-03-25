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
