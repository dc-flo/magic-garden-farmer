from time import sleep

import keyboard, webbrowser, yaml

def sell():
    keyboard.press_and_release("shift+3")
    keyboard.press_and_release("space")

def harvest():
    if keyboard.is_pressed("q"):
        exit()
    for i in range(5):
        keyboard.press_and_release('space')

def go_left():
    keyboard.press_and_release('a')
    sleep(.1)

def go_right():
    keyboard.press_and_release('d')
    sleep(.1)

def go_up():
    keyboard.press_and_release('w')
    sleep(.1)

def go_down():
    keyboard.press_and_release('s')
    sleep(.1)

def reset(x, y):
    keyboard.press_and_release('shift+2')
    for i in range(10 - x):
        go_left()
    for j in range(10 - y):
        go_up()

def farm(x, y):
    for j in range(y):
        for i in range(x-1):
            harvest()
            if j % 2 == 0:
                go_right()
            else:
                go_left()
        harvest()
        go_down()


if __name__ == '__main__':
    config = yaml.safe_load(open('./config.yml', 'r'))

    if config.get("new_window"):
        webbrowser.open("https://magiccircle.gg/", 1)
        input("Manage login, press Enter to continue...")

    print("waiting for " + str(config.get("timeout")) + " seconds")
    print("Press 'q' to exit at any time.")
    sleep(config.get("timeout"))
    print("starting farming...")

    while True:
        reset(0, 3)
        farm(21, 7)
        sell()