import pygame
import random
import time

# If the game is too difficult set the asteroid_counter to a lower number
# asteroid_counter is on line 57

# Initialising the pygame modules

pygame.init()
pygame.mixer.init()

# Creating the screen width and height
    
screen_width = 1040
screen_height = 680

# Creating color variables
white = (255, 255, 255)
red = (255, 0, 0)
black = (0, 0, 0)

# Creating the screen and applying the width and height variables

screen = pygame.display.set_mode((screen_width, screen_height))

# Creating the player, bullet, enemy and prize
# Player from: #Sprite from: https://www.subpng.com/png-igtkth/
# Prize from: https://www.pngegg.com/en/png-ertzr/download?width=80
# Bullet from: https://www.pixilart.com/draw/tank-bullet-99133f400f2a4f5
# For the enemy I used 3 different images of different sizes (all of asteroids)

player = pygame.image.load("player.png")
bullet = pygame.image.load("bullet.png")
prize = pygame.image.load("prize01.png")
enemy = pygame.image.load("enemy.png")
enemy2 = pygame.image.load("enemy2.png")
enemy3 = pygame.image.load("enemy3.png")

# pygame.mixer.init() initialises the mixer in order to use sound
# Creating different sound varaibles
# I looked at this site regarding adding in sound: https://opensource.com/article/20/9/add-sound-python-game
# The shot2 sound is from: https://freesound.org/people/Halgrimm/sounds/156895/
# Beep: https://freesound.org/people/peepholecircus/sounds/196979/
# prize: https://www.youtube.com/watch?v=kfcV9TXFFes&ab_channel=Luigi

shot = pygame.mixer.Sound("shot3.wav")
beep = pygame.mixer.Sound("beep.wav")
prize_sound = pygame.mixer.Sound("mario_prize1.wav")

pygame.mixer.set_num_channels(3)

# Initialising an asteroid couner.
# The user will need to destroy x amount of asteroids before they can claim their prize!
# I set this vlue to 3 but the value can be easily changed

asteroid_counter = 3

# Get the width and height of image for boundary detection

image_height = player.get_height()
image_width = player.get_width()

prize_height = prize.get_height()
prize_width = prize.get_width()

enemy_height = enemy.get_height()
enemy_width = enemy.get_width()

enemy2_height = enemy2.get_height()
enemy2_width = enemy2.get_width()

enemy3_height = enemy3.get_height()
enemy3_width = enemy3.get_width()

bullet_height = bullet.get_height()
bullet_width = bullet.get_width()   

# Print test to see if the boundary detection is working

print("This is the height of the player image: " +str(image_height))
print("This is the width of the player image: " +str(image_width))

# Creating position variables for the player

playerXposition = 100
playerYposition = 50

# Enemy(the asteroids) position will start off screen and at a random x and y position
# I added +500 onto the screen_width so that the asteroid will be randomly spawned somewhere off-screen
# This was done to prevent the asteroids all coming out at the same x and y positions

enemyXposition = random.randint(screen_width, screen_width + 500)
enemyYposition = random.randint(0, screen_height - enemy_height)

enemy2Xposition = random.randint(screen_width, screen_width + 500)
enemy2Yposition = random.randint(0, screen_height - enemy2_height)

enemy3Xposition = random.randint(screen_width, screen_width + 500)
enemy3Yposition = random.randint(0, screen_height - enemy3_height)

# Creating position variable for the prize

prizeXposition = 500
prizeYposition = screen_height/2

# Creating a bullet_fired variable and intializing it to False
# This will be used later to display the bullet

bullet_fired = False

# prize variable initialised to False

prize_on = False
prize_on_sound_started = False

# Creating a a "You Won" message:
# I looked at this site for help: https://www.geeksforgeeks.org/python-display-text-to-pygame-window/

font_style = pygame.font.SysFont("bahnschrift", 100)
small_font= pygame.font.SysFont("bahnschrift", 25)
message_win = font_style.render("You Win!", True, white)
message_winRect = message_win.get_rect()
message_winRect.center = (screen_width/2, screen_height/2)

# Creating a "You Lost" message:

message_lost = font_style.render("You Lost!", True, white)
message_lostRect = message_lost.get_rect()
message_lostRect.center = (screen_width/2, screen_height/2)

# Check to see which key has been pressed
# Initialising all to False

keyUp = False
keyDown = False
keyLeft = False
keyRight = False
keySpace = False

# Creating the game loop

while 1:
    screen.fill(0)      

    # Drawing player to specified position
    
    screen.blit(player, (playerXposition, playerYposition))
    
    # Drawing enemy to specified position
    
    screen.blit(enemy, (enemyXposition, enemyYposition))
    screen.blit(enemy2, (enemy2Xposition, enemy2Yposition))
    screen.blit(enemy3, (enemy3Xposition, enemy3Yposition))
        
    # Drawing prize to specified position
    # Prize will only be drawn once the asteroid counter reaches 0
    # Creating an if else statement to view number asteroids left
    # Prize will appear on when there are 0 asteroids left to defeat
    # When the prize is displayed a sound will play as well 
    
    if asteroid_counter <= 0:
        prize_on = True        
        # The following if statment checks if the sound is playing. Making sure it only plays once.        
        if prize_on_sound_started == False:
            pygame.mixer.Sound.play(prize_sound)
            prize_on_sound_started = True      
        # Prize message
        asteroids_left = small_font.render(f"Claim your prize! Be careful not to die!", True, white)
        asteroids_leftRect = asteroids_left.get_rect()
        asteroids_leftRect.center = (220, 20) 
    else:
        prize_on = False
        # Asteroids left to destroy
        # pygame.mixer.Sound.play(game_sound)        
        asteroids_left = small_font.render(f"Asteroids left: {asteroid_counter}", True, white)
        asteroids_leftRect = asteroids_left.get_rect()
        asteroids_leftRect.center = (100, 20)

    # Displaying the prize if the prize_on variable = True
        
    if prize_on == True:
        screen.blit(prize, (prizeXposition, prizeYposition))

    # Displaying the 'asteroids left' message or 'claim your prize' message
        
    screen.blit(asteroids_left, asteroids_leftRect)
   
    # Updating the screen
    
    pygame.display.flip()

    # Creating the boundary box for the player that will be used for collision detection
    # The following updates the playerBox position to the player's position = making the box stay around the player
    
    playerBox = pygame.Rect(player.get_rect()) 
    playerBox.top = playerYposition
    playerBox.left = playerXposition

    # Boundary box for the enemy(asteroids)
     
    enemyBox = pygame.Rect(enemy.get_rect())
    enemyBox.top = enemyYposition
    enemyBox.left = enemyXposition

    enemy2Box = pygame.Rect(enemy2.get_rect())
    enemy2Box.top = enemy2Yposition
    enemy2Box.left = enemy2Xposition

    enemy3Box = pygame.Rect(enemy3.get_rect())
    enemy3Box.top = enemy3Yposition
    enemy3Box.left = enemy3Xposition

    # Boudning box for the prize
    
    prizeBox = pygame.Rect(prize.get_rect())
    prizeBox.top = prizeYposition
    prizeBox.left = prizeXposition

    # Bullet position
    # The bullets position is declared within the game loop as it is dependent on the players moving position.
    # If the bullets position is declared outside the loop the bullet will stay static at the origninally declarded position of the player
    
    bulletXpos = playerXposition + 150
    bulletYpos = playerYposition + 20

    # Bounding box for bullet
    
    bulletBox = pygame.Rect(bullet.get_rect())
    bulletBox.top = bulletYpos
    bulletBox.left = bulletXpos 
    
    # Loops through events in the game
    
    for event in pygame.event.get():

        # Check to see if has been quit
        
        if event.type == pygame.QUIT:
            pygame.quit()
            exit(0)

        # Check to see if the user has pressed a key
            
        if event.type == pygame.KEYDOWN:            
            if event.key == pygame.K_UP:
                keyUp = True                
            if event.key == pygame.K_DOWN:
                keyDown = True                
            if event.key == pygame.K_LEFT:
                keyLeft = True                
            if event.key == pygame.K_RIGHT:
                keyRight = True                
            if event.key == pygame.K_SPACE:
                keySpace = True                

        # Check to see if key has not been pressed
                
        if event.type == pygame.KEYUP:
            if event.key == pygame.K_UP:
                keyUp = False                
            if event.key == pygame.K_DOWN:
                keyDown = False                
            if event.key == pygame.K_LEFT:
                keyLeft = False                
            if event.key == pygame.K_RIGHT:
                keyRight = False                
            if event.key == pygame.K_SPACE:
                keySpace = False
                bullet_fired = False
                
    # Once events are checked add in parameter to move the players position and fire the bullet
                
    if keyUp == True:
        if playerYposition > 0:
            playerYposition -=1
    if keyDown == True:
        if playerYposition < screen_height - image_height:
            playerYposition +=1
    if keyLeft == True:
        if playerXposition > 0:
            playerXposition -=1
    if keyRight == True:
        if playerXposition < screen_width - image_width:
            playerXposition +=1            
    if keySpace == True:
        screen.blit(bullet, (bulletXpos, bulletYpos))  
        bullet_fired = True        

    # Displaying the bullet if the bullet_fired variable is true
    # A bullet sound will also play and the display will be updated to view the bullet
        
    if bullet_fired == True:
        screen.blit(bullet, (bulletXpos, bulletYpos))
        pygame.mixer.Channel(0).play(shot)
        pygame.display.update()
        
    # Test player collision of the boxes:
    # If the player collides with an aseroid, they will lose the game
    # A "You Lost" message will appear on. There will be a time delay before the game quits
    # I looked at this site regarding the time.sleep() function: https://www.journaldev.com/15797/python-time-sleep
        
    if playerBox.colliderect(enemyBox) or playerBox.colliderect(enemy2Box) or playerBox.colliderect(enemy3Box):
        screen.blit(message_lost, message_lostRect)
        pygame.display.update()
        time.sleep(2)
        pygame.quit()
        exit()

    # If the user touches the prize, the user wins
        
    if playerBox.colliderect(prizeBox) and prize_on == True:
        screen.blit(message_win, message_winRect)
        pygame.display.update()
        time.sleep(2)
        pygame.quit()
        exit()
        
    # If the bullet touches an enemy
    # The enemy/asteroid will be respawned again off screen
    # This is done through resetting the X and Y position of the object
    # Once an enemy/asteroid has been hit, the counter will be decremented
        
    if bulletBox.colliderect(enemyBox) and bullet_fired == True:       
        enemyXposition = random.randint(screen_width, screen_width + 500)
        enemyYposition = random.randint(0, screen_height - enemy_height)
        pygame.mixer.Channel(1).play(beep)
        asteroid_counter -=1
        
    if bulletBox.colliderect(enemy2Box) and bullet_fired == True:       
        enemy2Xposition = random.randint(screen_width, screen_width + 500)
        enemy2Yposition = random.randint(0, screen_height - enemy2_height)
        pygame.mixer.Channel(1).play(beep)
        asteroid_counter -=1

    if bulletBox.colliderect(enemy3Box) and bullet_fired == True:       
        enemy3Xposition = random.randint(screen_width, screen_width + 500)
        enemy3Yposition = random.randint(0, screen_height - enemy3_height)
        pygame.mixer.Channel(1).play(beep)
        asteroid_counter -=1

    # Makes the enemy approach the  player and determines the speed
        
    enemyXposition -= 0.1
    enemy2Xposition -= 0.2
    enemy3Xposition -= 0.1
      
    # Respawing enemy/asteroids
    # If the enemy/asteroid reachs the end of the screen they will be respawned again
    # This is done through resetting their X and Y position
        
    if enemyXposition <= 0:
        enemyXposition = random.randint(screen_width, screen_width + 500)
        enemyYposition = random.randint(0, screen_height - enemy_height)
        
    if enemy2Xposition <= 0:
        enemy2Xposition = random.randint(screen_width, screen_width + 500)
        enemy2Yposition = random.randint(0, screen_height - enemy2_height)

    if enemy3Xposition <= 0:
        enemy3Xposition = random.randint(screen_width, screen_width + 500)
        enemy3Yposition = random.randint(0, screen_height - enemy3_height)       
 
