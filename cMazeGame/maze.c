#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int mapRows = 0;
int mapCols = 0;
char *map;

struct game_object {
    int xCord;
    int yCord;
    char type;
};

struct list_node {
    struct game_object *character;
    struct list_node *next;
};

char get(unsigned x, unsigned y) {
    int position = ((mapCols * y) + x);
    return map[position];
}

void put(unsigned x, unsigned y, char c) {
    int position = ((mapCols * y) + x);
    map[position] = c;
}

//fills the map with walls, treasure and the empty spaces
void initalise() {
    int i;
    int j;
    
    //adding walls
    for(i = 0; i < mapRows; i++) {
        for(j = 0; j < mapCols; j++) {
            int position  = ((mapCols * i) + j);
            
            if (i == 0 || i == mapRows - 1 || j == 0 || j == mapCols - 1)
                map[position] = '#';
            else
                map[position] = ' ';
        }
    }
    
    //adding treasure
    put(1,1,'*');
}

void print() {
    int i;
    int j;
    
    for(i = 0; i < mapRows; i++) {
        for(j = 0; j < mapCols; j++) {
            int position  = ((mapCols * i) + j);
            printf("%c", map[position]);
        }
        printf("\n");
    }
}

void put_object(struct game_object *obj) {
    put(obj->xCord, obj->yCord, obj->type);
}

void add_monster(struct list_node **head, unsigned x, unsigned y) {
    struct list_node *newNode = (struct list_node*)malloc(sizeof(struct list_node*));
    struct game_object *newMonster = (struct game_object*)malloc(sizeof(struct game_object*));
    
    //assigning variables for the new monster
    newMonster->xCord = x;
    newMonster->yCord = y;
    newMonster->type = 'M';
    
    //linking game object and new node to list
    newNode->character = newMonster;
    newNode->next = *head;
    *head = newNode;
}

void free_list(struct list_node *iter) {
    struct list_node *current = NULL;
    while ((current = iter) != NULL) {
        iter = iter->next;
        free(current->character);
        free(current);
    }
}

void print_list(struct list_node **head) {
    struct list_node *temp = NULL;
    temp = *head;
    
    if (temp->next == NULL) {
        printf("List is empty\n");
    }
    else {
        while (temp != NULL) {
            printf("Monster with xPos %d and yPos %d\n", temp->character->xCord, temp->character->yCord);
            temp = temp->next;
        }
    }
}

//moves the object, in the given direction. If move is legal (not a wall), returns character which is on tile the object is moving to
char move_object(struct game_object *obj, int direction) {
    int newX = obj->xCord;
    int newY = obj->yCord;
    char tileMovingTo = '\0';
    
    //adjusting the new desired positions
    switch(direction) {
        case 0: //up
        newY = newY - 1;
        break;
        case 1: //down
        newY = newY + 1;
        break;
        case 2: //left
        newX = newX - 1;
        break;
        case 3: //right
        newX = newX + 1;
        break;
        default: //any other key
        break;
    }
        
    tileMovingTo = get(newX, newY);

    //if legal move (not moving into wall), update coords of obj
    if (tileMovingTo != '#') {
        put(obj->xCord, obj->yCord, ' '); 
        obj->xCord = newX;
        obj->yCord = newY;
    }

    return tileMovingTo;
}

//converts the input to an int, which represents a direction
int convertInputToDirection(char c) {
    switch(c) {
        case 'w': //up
        return 0;
        break;
        case 's': //down
        return 1;
        break;
        case 'a': //left
        return 2;
        break;
        case 'd': //right
        return 3;
        break;
        default: //any other key
        return 4;
        break;
    }
}

//Analyses user input, moves player accordingly. Draws monster and player to map. Moves monsters one space in either direction. Returns correct game state (win, loss, continue)
int updateGame(struct game_object *player, struct list_node **head, char c) {
    char newTile;
    char monsterTile;
    
    //iterate through monsters list and move monsters, one space in random direction
    struct list_node *temp = NULL;
    temp = *head;
    while (temp != NULL) {
        
        //if not start of game, DO NOT move monsters
        if (c != '\0') {
            //generates random direction and moves monster
            int monsterDirection = rand() % 4;
            monsterTile = move_object(temp->character, monsterDirection);
        }
        put_object(temp->character);
        temp = temp->next;
    }
    
    //if not start of game
    if (c != '\0') {
        
        //interpretting the movement input
        newTile = move_object(player, convertInputToDirection(c));
        printf("Player moving to tile with [%c]\n", newTile);
    }
    
    //moving player
    put_object(player);
    
    //returning game state
    if (newTile == 'M') { //if hit monster LOSE
        printf("Hit monster, you lose!\n");
        return 2;
    }
    else if (newTile == '*') { //if treasure WIN
        printf("Found treasure, you win!\n");
        return 1; 
    } else { //continue game
        return 0; 
    }
}

void save_map(const char *file_name) {
    FILE *file;
    file = fopen(file_name, "w");
    int i;
    int j;
    
    for(i = 0; i < mapRows; i++) {
        for(j = 0; j < mapCols; j++) {
            int position  = ((mapCols * i) + j);
            fprintf(file, "%c", map[position]);
        }
        fprintf(file, "%c", '\n');
    }
    fclose(file);
}

void rowsColsCount(const char *file_name) {
    FILE *file;
    file = fopen(file_name, "r");
    char chr;

    while ((chr = getc(file)) != EOF) {
        mapCols++;
        if (chr == '\n') {
            mapRows++;
        }
    }
    
    mapCols = mapCols / mapRows - 1;
    fclose(file);
}

void load_map(const char *file_name) {
    FILE *file;
    file = fopen(file_name, "r");
    int i = 0;
    int j;
    
    char chr;

    while ((chr = getc(file)) != EOF) {
        if (chr != '\n') {
            map[i] = chr;
            i++;
        }
    }
    
    fclose(file);
}

void parse_map(struct game_object *player, struct list_node **head) {
    
    free_list(*head);
    
    //iterate over map
    int i;
    int j;
    char current;
    
    for (i = 0; i < mapRows; i++) {
        for (j = 0; j < mapCols; j++) {
            current = get(j, i);
            if (current == '@') { //if player found
                player->xCord = j;
                player->yCord = i;
            }
            if (current == 'M') { //if monster found
                add_monster(head, j, i);
            }
        }
    }
    
    //if char M, get coords and add monster to list
    
    //if player, assign new variables
    
}

int main(int argc, char *argv[]) {
    //initalising game - applies to loaded maps and normal map
    char input = '\0';
    srand(time(NULL));
    struct list_node *listHead = NULL;
    struct game_object *newPlayer = (struct game_object*)malloc(sizeof(struct game_object*));
    newPlayer->type = '@';
    
    //initalising game map array and row/col sizes
    if (argc < 2) {
        printf("Loading default map\n");
        mapRows = 5;
        mapCols = 12;
    } else {
        printf("Loading map from: %s\n", argv[1]);
        rowsColsCount(argv[1]);
    }
    
    char gameMap[mapRows][mapCols];
    map = &gameMap[0][0];
    
    printf("There are %d rows in file\n", mapRows);
    printf("There are %d columns in file\n", mapCols);
    
    //initalising the map layout
    if (argc < 2) {
        initalise();

        newPlayer->xCord = 3;
        newPlayer->yCord = 3;
        
        add_monster(&listHead, 4, 3);
        add_monster(&listHead, 8, 2);
        
    } else {
        load_map(argv[1]);
        parse_map(newPlayer, &listHead);
        print_list(&listHead);
    }
    
    updateGame(newPlayer, &listHead, input);
    
    //game rounds, pressing x will quit game
    while (input != 'x') {
        
        //taking user input for direction of movement
        print();
        scanf(" %c", &input);

        if (input == 'k') { //save map
            save_map("savegame.txt");
            printf("Map saved.\n");
        } else if (input == 'l'){ //load map
            load_map("savegame.txt");
            parse_map(newPlayer, &listHead);
            printf("Map loaded.\n");
        } else { //continue with round
            
            //moving obj and checking for win/loss
            int result = updateGame(newPlayer, &listHead, input);
            
            //ending game if win or lose
            if (result == 1 || result == 2) {
                return 0;
            } 
        }
    }
    
    return 0;
}
