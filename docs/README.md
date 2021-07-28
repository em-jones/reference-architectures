# Register Application

## Cash Register
### Workflows
#### Show: A _user_ can inquire about the state of the cash register.
- TotalAmountPresent
- Number of bills with denominations:
  - 20
  - 10
  - 5
  - 2
  - 1
- Formatted as : $TotalAmountPresent NumberOfTwenties NumberOfTens NumberOfFives NumberOfTwos NumberOfOnes
- Requests: 
  - QueryStatusOfCashRegister
    - StateInquiry
    - OutputFormatter
- Responses:
  - StatusOfCashRegister
    - State
    - FormattedOutput
  
#### PutIntoRegister(and Show): A _user_ can add money to the register in the form of supported denominations
- Supported Denominations
- Commands:
  - PutCashInRegister
    - Denominations (Set)
    - OutputFormatter
- Results:
  - CashAddedToRegister
    - FormattedOutput
- SideEffects:
  - Printing of the update state
- Validations:
  - PutCashInRegister
    - No negatives
    
#### TakeFromRegister(and Show): A _user_ can remove money to the register in the form of supported denominations
- Supported Denominations
- Commands:
  - RemoveCashFromRegister
    - Denominations (Set)
    - OutputFormatter
- Results:
  - CashRemovedFromRegister
    - FormattedOutput
- SideEffects:
  - Printing of the update state
- Validations:
  - RemoveCashFromRegister
    - No negatives
- Exceptions:
  - OutOfFunds
  
#### MakeChangeFromRegister(PutIntoRegister and TakeFromRegister): A _user_ can use the register in order to make change
for an arbitrary combinations of denominations
- Commands:
  - MakeChange
  
#### QuitRegister: A _user_ can leave the program
    
  
    