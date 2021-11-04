import math

# Ask the user to choose either bond or investment
while (True):
    option = input('''Choose either 'investment' or 'bond' from the menu below to proceed:
    investment \t - to calculate the amount of interest you'll earn on interest
    bond \t \t - to calculate the amount you'll have to pay on a home loan \n''')

    option = option.lower()

    # Displaying an error message if the user did not type in a valid input
    if (option != "bond" and option != "investment" ):
        print("You have not entered a valid option \n")
        continue
    else:
        break
    
# If the user selects 'investment'
if (option == "investment"):        
    
    amount = float(input("\nEnter the amount you are depositing: "))
    
    interest = float(input("\nEnter your interest rate as a percentage: "))
    r = interest/100
    
    years = int(input("\nHow many years do you plan to invest for: "))

    # Working out the interest rate based on either simple or compound
    while (True):
        interest = input("\nDo you want 'simple' or 'compound' interest: ")
        interest = interest.lower()

        if (interest == "simple"):
            total_amount = amount*(1 + r*years)        
            break
        elif (interest == "compound"):
            total_amount = amount*math.pow((1 + r), years)
            break
        else: 
            print("\nInvalid option!")
            continue
        
    total_amount = round(total_amount, 2)
    print(f"\nThe total amount of interest you will earn is is: R{total_amount}")

# If the user selects 'bond'   
elif (option == "bond"):
    
    value = float(input("\nEnter the currecnt value of the house (eg. 100400): "))
    
    interest = float(input("\nEnter your interest rate as a percentage: "))
    monthly_interest = (interest/100)/12
    
    months = int(input("\nHow many months will you take to repay the bond (eg. 150): "))

    # Working out the total monthly bond repayment
    total_amount = (monthly_interest*value)/(1 - math.pow(1 + monthly_interest, - months))

    total_amount = round(total_amount, 2)
    print(f"\nThe total monthly repayment for you bond is: R{total_amount}")


    
