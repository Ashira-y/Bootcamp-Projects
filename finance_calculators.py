import math

#Ask the user to choose either bond or investment
#I used the ''' ''' so that it keeps my line spacing when I add in a new line

option = input('''Choose either 'investment' or 'bond' from the menu below to proceed:
investment \t - to calculate the amount of interest you'll earn on interest
bond \t \t - to calculate the amount you'll have to pay on a home loan \n''')

#Converting the option chosen all to lowercase (capitalisation should not affect how the prgram proceedes)

option = option.lower()

#Displaying an error message if the user did not type in a valid input

if (option != "bond" and option != "investment" ):
    print("You have not entered a valid option")
    exit()

print('\n')
    
#If the user selects 'investment'
#Ask the user to input:
#The amount of money that they are depositing.
#The interest rate as a %
#The number of years they plan on investing for.
#Then ask the user to input whether they want “simple” or “compound” interest, and store this in a variable called interest.

if (option == "investment"):        
    
    amount = float(input("Enter the amount you are depositing: "))
    
    interest = float(input("Enter your interest rate as a percentage: "))
    r = interest/100
    
    years = int(input("How many years do you plan to invest for: "))

    interest = input("Do you want 'simple' or 'compound' interest: ")
    interest = interest.lower()

    #Working out the interest rate based on either simple or compound
    #I used the formula's given in the exercise to work out the total amount
    
    if (interest == "simple"):
        total_amount = amount*(1 + r*years)        
    else:
        total_amount = amount*math.pow((1 + r), years)
        
    total_amount = round(total_amount, 2)
    print('\n')
    print(f"The total amount of interest you will earn is is: R{total_amount}")

#If the user selects 'bond'
#Ask the user to input:
#The present value of the house. E.g. 100000
#The interest rate as a %
#The number of months they plan to take to repay the bond. E.g. 120
    
elif (option == "bond"):
    
    value = float(input("Enter the currecnt value of the house (eg. 100400): "))
    
    interest = float(input("Enter your interest rate as a percentage: "))
    monthly_interest = (interest/100)/12
    
    months = int(input("How many months will you take to repay the bond (eg. 150): "))

    #Working out the total monthly bond repayment based on the formula given
    
    total_amount = (monthly_interest*value)/(1 - math.pow(1 + monthly_interest, - months))

    total_amount = round(total_amount, 2)
    print('\n')
    print(f"The total monthly repayment for you bond is: R{total_amount}")


    
