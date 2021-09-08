from datetime import date
import datetime
import os.path

# DEFINING DIFFERENT FUNCTION:
# reg_user function
def reg_user():
    stored_users =  get_list_of_users()

    # Using a while loop to check to see if the username and password match when asked to confirm details.
    # The while loop also checks that the username hasn't been used before
    matches = False
    while matches == False:
        new_username = input("Enter a new username: ")
        if new_username in stored_users:
            print("Username taken, choose a different username.\n")
            continue # Continue skips the rest of loop and moves to the next iteration
        new_password = input("Enter a new password: ")
        confirm_username = input("\nConfirm your username: ")
        confirm_password = input("Confirm your password: ")       

        # If the new username and password matches the confirmend details,
        # The new username and password will be written to the users.txt file
        if new_username == confirm_username and new_password == confirm_password:
            append_to_file("user.txt",f"\n{new_username}, {new_password}" )
            print("\nThe user has been added!")
            matches = True
            break        
        elif matches == False:
            print("\nIncorrect, re-enter details!\n")

# add_task function
def add_task():
    tasks = get_all_tasks()
    list_users = get_list_of_users()
    
    # The name of the assigned task can only be assigned to a valid user within the users.txt file
    # Using a while loop to check if the username is valid
    valid_user = False
    while valid_user == False:
            a_username = input("Enter the persons username or 'q' to return to the menu: \n")
            if a_username in list_users:
                valid_user = True
            elif a_username == 'q' or a_username == 'Q':
                return
            else:
                print("\nUser doesn't exist. Please enter a valid user.\n")

    a_task = input("\nEnter the task: \n")
    a_description = input("\nEnter a description of the task: \n")
    a_assigned = todays_date() 
    due_date = create_due_date()
    a_completed = "No"

    # Once the info has been collected, it will be written to the tasks.txt file
    append_to_file("tasks.txt", f"\n{a_username}, {a_task}, {a_description}, {a_assigned}, {due_date}, {a_completed}")

# Append to file function.
def append_to_file(file_name, item_to_write):
    file = open(file_name, "a", encoding="utf-8")
    file.write(item_to_write)
    file.close()

def create_due_date():
    # This function checks to see if the date entered if in a valid format. If the format is invalid an error message will appear.
    # The user will be prompted to re-enter the due date

    entered_date = False
    while entered_date == False:
        # Check to see if the date is valid.
        # Used a try/except to give validate the date. I looked at this site: https://www.codevscolor.com/date-valid-check-python
        due_date = input("\nEnter the due date of the task (yyyy-mm-dd): \n")
        try:
            datetime.datetime.strptime(due_date, '%Y-%m-%d')
            entered_date = True
        except ValueError:
            entered_date = False
        if entered_date == False:
            print("Date format is incorrect!")
    return(due_date)

# view_all function
def view_all():
    tasks = get_all_tasks()
    # Using a for loop to iterate over each task in the tasks.txt file and display them in the below format.
    for line in tasks:
        line_count, user_name, task, description, date_assigned, due_date, completed = line
        print(f'''
Task {line_count + 1}:
Username - {user_name}
Task -  {task}
Description - {description}
Date asigned - {date_assigned}
Due date - {due_date}
Completed - {completed} ''')

def get_all_tasks():
    tasks = open("tasks.txt", "r", encoding="utf-8" )
    all_tasks = []
    line_number = 0

    # Splitting up the different items into variables and adding them to the all_tasks list. 
    # Adding in the line numbers to the all_tasks list.
    # Returning all_tasks list
    for line in tasks:
        user_name, task, description, date_assigned, due_date, completed = line.replace("\n","").split(", ")
        all_tasks.append([line_number, user_name, task, description, date_assigned, due_date, completed])   
        line_number += 1
    tasks.close()
    return(all_tasks)
        
def get_number_of_users():
    users = open("user.txt", "r", encoding="utf-8")
    # Using a foor loop to increment the number_users counter.
    number_users = 0
    for user in users:
        number_users += 1
    users.close()
    return(number_users)    

def get_list_of_users():
    users = open("user.txt", "r+", encoding="utf-8") 
    list_users = []

    # Adding each user to list_users list and returning the list
    for user in users:
        user_name, password = user.replace("\n","").split(", ")
        list_users.append(user_name)
    users.close()
    return(list_users)

def get_number_of_tasks():
    tasks = open("tasks.txt", "r", encoding="utf-8")
    # Using a foor loop to increment the number_tasks counter.
    number_tasks = 0
    for task in tasks:
        number_tasks += 1
    tasks.close()
    return(number_tasks)

def get_current_user_tasks(tasks, current_user):
    current_user_tasks = []

    for line in tasks:
        # Don't need to re-split as it's already in a list format. Assigning different items to variables
        line_number, user_name, task, description, date_assigned, due_date, completed = line
        # Storing the current users tasks into a list
        if current_user == user_name:
            current_user_tasks.append(line)
    # The returned value will be a list containing the current users tasks
    return(current_user_tasks)

def displaying_user_tasks(current_user_tasks):
    # This function takes in the current users tasks and displays it in a formatted string
    # Loop through each task in the list.
    # The tasks details have been stored in a list which I assigned individual varibale names to the items within each task.
     for count,line in enumerate(current_user_tasks, 1):
        line_number, user_name, task, description, date_assigned, due_date, completed = line
        print(f"\nTask {count}:\n" + f"User - {user_name}\nTask - {task}\nDescription - {description}\nDate Assigned - {date_assigned}\nDue Date - {due_date}\nComplete - {completed}")

def editing_users_task(users_task_to_edit, stored_users):
    # Storing the different elements of the task into variables
    line_number, user_name, task, description, date_assigned, due_date, completed = users_task_to_edit

    # If the task has already been completed display a message saying so.
    # Else, edit the task.
    if completed == 'Yes':
        print("\nThis task is has been completed!")
        return (False, users_task_to_edit)
    else:
        mark_complete = input("\nMark the task complete ('yes') or edit the task ('no'): \n")
        # Marking the task complete if user types in 'yes'
        if mark_complete.lower() == 'yes' and completed == 'No':
            completed = 'Yes'        
        elif mark_complete.lower() == 'no' and completed == 'No':
            # Editing the username of the task
            # Using a while loop to get the user to enter in an existing username or press 'q' to return to menu
            valid_user = False
            while valid_user == False:
                new_user = input("\nWho do you want to assign this task to or 'q' to return to menu: \n")                
                if new_user in stored_users:
                    user_name = new_user
                    valid_user = True
                elif new_user == "q" or new_user == 'Q':
                    return(False, users_task_to_edit)
                else:
                    print("\nUser doesn't exist. Please enter a valid username!\n")
            # Edting the due date of the task. Using create_due_date() function to see if the date is in a valid format.
            due_date = create_due_date()
        return(True, [line_number, user_name, task, description, date_assigned, due_date, completed])  
                

def writing_to_file(file_name, item_to_write):
    file = open(file_name, "w", encoding="utf-8")
    file.write(item_to_write)
    file.close()

def date_of_object(item):
    date_object = datetime.datetime.strptime(item, '%Y-%m-%d')
    return(date_object)

def todays_date():
    # I looked at this site to get todays date and not the time: https://stackoverflow.com/questions/32490629/getting-todays-date-in-yyyy-mm-dd-in-python
    date =  datetime.datetime.today().strftime('%Y-%m-%d')
    return(date)

# view_mine function
def view_mine(current_user):
    # Creating variables that store the results of the function.
    different_users = get_list_of_users()    
    all_tasks_list = get_all_tasks()     
    current_user_tasks = get_current_user_tasks(all_tasks_list, current_user)

    # The function prints, it does not have a return type (Don't need to assign it to a variable name)
    displaying_user_tasks(current_user_tasks)

    # Creating a while loop that will ask the user to enter a task they want to edit until they enter a valid task.
    valid_task_to_edit = False
    while valid_task_to_edit == False:
        # Asking user if they want to edit a task or press -1 to return.
        task_to_edit = input("\nEnter the number of the task you want to edit or enter '-1' to return to the main menu: \n")

        # Check to see if entered input is an int or not.
        # If entered input isn't a digit an error message will be displayed.
        if task_to_edit == "-1":
            return()
        elif task_to_edit.isdigit():
            task_to_edit = int(task_to_edit)
            # If -1 was entered, you will be returned to the main menu.
            if task_to_edit > len(current_user_tasks) or task_to_edit <= -2 or task_to_edit == 0:                        
                print("\nPlease select a valid task to edit!")
                valid_task_to_edit = False
            else:
                valid_task_to_edit = True
                # Editing the current users task. Storing the different outputs of the fucntion into variables.
                updated, edited_task = editing_users_task(current_user_tasks[task_to_edit -1], different_users)

                # If the task was updated then replace the old task
                # Overwrting the old task with the updated task. This is done through indexing the line number of the task and replacing it with the new task.
                # editing_users_task returns a bool value and a list. This allows me to check if the task has been edited and to get the new edited task.
                if updated == True:                    
                    all_tasks_list[edited_task[0]] = edited_task
                    tasks_to_write = ""                
                    for task in all_tasks_list:                
                        line_number, user_name, task, description, date_assigned, due_date, completed = task
                        tasks_to_write += f"{user_name}, {task}, {description}, {date_assigned}, {due_date}, {completed}\n"
                    # Writing the task to the tasks.txt file.
                    writing_to_file("tasks.txt", tasks_to_write)        
        else: 
            print("\nPlease select a valid task to edit!")


# Read from file function that open the file, uses a for loop to iterate and print each line before closing the file.
def read_and_print_from_file(file_to_read):
    file = open(file_to_read, "r", encoding="utf-8")
    for i in file:
        i = i.replace("\n", "")
        print(i)
    file.close()

# user_stats function
def  display_statistics():
    # If the user_overview.txt and task_overview.txt haven't been created, they will first be created before the statistics are displayed.
    reports = False
    while reports == False:
        # I looked at this site to check if the files exist: https://careerkarma.com/blog/python-check-if-file-exists/
        if os.path.exists("user_overview.txt") == False or os.path.exists("task_overview.txt") == False:
            generate_reports()
            reports = False
        else:
            reports = True
            print("\nTASK STATISTICS: ")
            # Reading from the file in order to display the statistics.
            task_stats = read_and_print_from_file("task_overview.txt")                     
            print("USER STATISTICS: ")
            user_stats = read_and_print_from_file("user_overview.txt")    

def task_overview():    
    tasks = get_all_tasks() 
    number_tasks = get_number_of_tasks()
    completed_tasks = 0
    incomplete_tasks = 0
    overdue_tasks = 0

    for line in tasks:        
        line_number, user_name, task, description, date_assigned, due_date, completed = line
        due_date_obj = date_of_object(due_date)
        current_date = date_of_object(todays_date())
        was_before = due_date_obj < current_date

        # Check to see if the task has been completed and adding 1 onto completed_tasks
        # Check to see if the task is incomplete and adding 1 onto incomplete_tasks
        # Check to see if the task is incomplete and overdue and adding 1 onto overdue_tasks
        if completed == "Yes":
            completed_tasks += 1        
        elif completed == "No":    
            incomplete_tasks += 1            
            if was_before == True:
                overdue_tasks += 1
    
    # Working out the percentage of incomplete tasks and then the percentage of overdue tasks
    percentage_incomplete = percentage(incomplete_tasks, number_tasks) 
    percentage_overdue = percentage(overdue_tasks, number_tasks)
    
    # The writing_to_file() function creates and closes the file. I used the ''' ''' notation when writin in order to write to file with the correct indenting and line spacing etc. 
    # I know that the indents do not follow the indents within this method, however, I thought that writing the file using ''' ''' is easier to follow and understand.
    writing_to_file("task_overview.txt", f'''
Number of tasks: {number_tasks}
Completed tasks: {completed_tasks}
Incomplete tasks: {incomplete_tasks}
Overdue tasks: {overdue_tasks}
Percentage of incomplete tasks: {percentage_incomplete}%
Percentage of overdue tasks: {percentage_overdue}%
    ''')

def printing_dic(dic_a):
    for i in dic_a:
        print(f"{i}: {dic_a[i]}")

def writing_dic(dic_a, file):
    for i in dic_a:
        file.write(f"{i}: {dic_a[i]}\n")

def dictionary_to_string(dictionary):
    list_values = []
    for i in dictionary:
        list_values.append(f"{i}: {dictionary[i]}\n")
    return("".join(list_values))
        
def percentage(a, b):
    # Working out the percentage of two int. If either number is 0, 0 will be returned.
    if a == 0 or b == 0:
         return(0)
    elif a == 0 and b == 0:
        return(0)
    else:
        percent = (a / b) * 100
        return(round(percent,2))

def user_overview():
    users = get_list_of_users()
    tasks =  get_all_tasks()
    number_tasks = get_number_of_tasks()
    number_users = get_number_of_users()

    # Creating different dictionaries relating to different elements that need to be displayed
    tasks_assigned_to_user = {}    
    percentage_tasks_assigned = {}    
    percentage_tasks_completed = {}
    incomplete_tasks = {}
    percentage_tasks_incompleted = {}    
    incomplete_overdue = {}

    # For loop that sets the key to the different dicionaries to the users name and sets a default value of 0                
    for user in users:
        tasks_assigned_to_user[user] = 0        
        percentage_tasks_completed[user] = 0
        incomplete_overdue[user] = 0
        incomplete_tasks[user] = 0

    # The for loop is also used to split up each line in tasks into different variables
    for line in tasks:        
        line_number, user_name, task, description, date_assigned, due_date, completed = line
        due_date_obj = date_of_object(due_date)
        current_date = date_of_object(todays_date())

        # https://stackoverflow.com/questions/1602934/check-if-a-given-key-already-exists-in-a-dictionary
        # If the username is in tasks tasks_assigned_to_user than their value will be incremented.
        # If the task has been completed the value relating to the user in percentage_tasks_completed dictionary will be incremenet.
        # If the task has not been completed the value relating to the user in incomplete_tasks dictionary will be incremenet.
        # If the task is incomplete and overdue the value relating to the user in incomplete_overdue dictionary will be incremenet.
        if user_name in tasks_assigned_to_user:
            tasks_assigned_to_user[user_name] += 1        
        if completed == 'Yes':
            percentage_tasks_completed[user_name] += 1        
        elif completed == 'No':
            incomplete_tasks[user_name] +=1        
        if due_date_obj < current_date and completed == 'No':
            incomplete_overdue[user_name] += 1
    
    # Working out the percentage of different elements in the dictionaries
    for i in tasks_assigned_to_user:
        percentage_tasks_assigned[i] = percentage(tasks_assigned_to_user[i], number_tasks)    
    for i in percentage_tasks_completed:
        percentage_tasks_completed[i] = percentage(percentage_tasks_completed[i], tasks_assigned_to_user[i])    
    for i in incomplete_tasks:
        percentage_tasks_incompleted[i] = percentage(incomplete_tasks[i], tasks_assigned_to_user[i])  
    for i in incomplete_overdue:
        incomplete_overdue[i] = percentage(incomplete_overdue[i], incomplete_tasks[i])  
   
    # The writing_to_file() function creates and closes the file. I used the ''' ''' notation when writin in order to write to file with the correct indenting and line spacing etc. 
    # I know that the indents do not follow the indents within this method, however, I thought that writing the file using ''' ''' is easier to follow and understand.
    writing_to_file("user_overview.txt", f'''
Number of users: {number_users}
Number of tasks: {number_tasks}\n
Tasks assigned to each user:
{dictionary_to_string(tasks_assigned_to_user)}
Percentage of tasks assigned to user:
{dictionary_to_string(percentage_tasks_assigned)}
Percentage tasks completed:
{dictionary_to_string(percentage_tasks_completed)}
Percentage tasks incomplete:
{dictionary_to_string(percentage_tasks_incompleted)}
Percentage tasks incomplete and overdue:
{dictionary_to_string(incomplete_overdue)}
    ''')

def generate_reports():
    # Creating the two files
    print("\nReports have been generated\n")
    task_overview()
    user_overview()

# MAIN FUNCTION THAT CODE WILL BE EXECUTED IN 
def main():
    # Opening the userr.txt file
    # Creating an array to store the login details in
    # Seperating the user text file into username and password
    users = open("user.txt", "r+")    
    login_details = []

    for user in users:
        user = user.replace("\n","")
        user_name_password = user.split(", ")
        #creates an array within an array
        login_details.append(user_name_password)    

    # Creating a login variable to determine if the username and password are correct
    # Using a while loop to ask user to enter in their details until the details are correct.
    login = False

    while login == False:        
        user_login = input("Username: ")
        password_login = input("Password: ")

        # storing the entered details in an array
        entered_details = []
        entered_details.append(user_login)
        entered_details.append(password_login)

        # Using a for loop to see if the entered details matches the username and password combo stored in users.txt
        for login_detail in login_details:        
            if login_detail == entered_details:
                login = True
                break              
        if login == False:
            print("Incorrect, try again!\n")

    # Displaying a menu once the user has successfully logged in.
    # There will be a different menu for the 'admin'.
    # Using a while loop that will continue to display the menu until 'e' to exit has been entered.
    exit_menu = False
    while exit_menu == False:
        # Selection for admin
        # Referencing the username aspect of the array and seeing if it is equal to "admin"
        if entered_details[0] == "admin":
            selection = input('''
            Please select one of the following options:
            r - register user
            a - add task
            va - view all tasks
            vm - view my tasks
            gr - generate reports
            ds - task statistics
            e - exit
            ''')
        # Menu for everyone else
        else:
            selection = input('''
            Please select one of the following options:
            a - add task
            va - view all tasks
            vm - view my tasks
            e - exit
            ''')

        # Making the selection lowercase incase user types in uppercase
        selection = selection.lower()
        # Registering a user, only the admin can access this
        if selection == "r" and entered_details[0] == "admin":
            reg_user()                       
        # Statistics component, only the admin can access this
        elif selection == "ds" and entered_details[0] == "admin":
            display_statistics() 
        # Generating statistics, only the admin can access this
        elif selection == "gr" and entered_details[0] == "admin":
            generate_reports()       
        # Adding a task
        elif selection == "a":
            add_task()            
        # View all tasks
        elif selection == "va":
            view_all()                
        # Viewing my tasks        
        elif selection == "vm":
            view_mine(entered_details[0])
        elif selection == "e":
            exit_menu = True
            break  
        # If selction is invalid - ask the user to reselect    
        else:
            print("\nSelection invalid. Please reselect and option!")     
            continue    
        
        users.close()

main()
