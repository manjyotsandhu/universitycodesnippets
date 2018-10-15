#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#define BUF_LEN 4

char *SERVER_ADDR;
in_port_t SERVER_PORT;

//If creating game
int do_codemaker(in_port_t portno) {
    
    //variables
    int sockfd;
    struct sockaddr_in serveraddr;
    struct sockaddr_in clientaddr;
    socklen_t clientaddrlen = sizeof(clientaddr);
    char buffer[BUF_LEN];
    char code[BUF_LEN];
    char feedback[BUF_LEN];
    
    //creating and binding datagram socket
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    memset(&serveraddr, 0, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_port = htons(SERVER_PORT);
    serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
    
    bind(sockfd, (struct sockaddr*)&serveraddr, sizeof(serveraddr));

    //prompt user for the code
    printf("Enter the code: ");
    scanf("%4s", code);

    //repeats receiving input/giving feedback until BBBB entered as feedback
    while (memcmp(feedback, "BBBB", 4) != 0) {
        
        //receive guess from codebreaker and obtain address
        recvfrom(sockfd, buffer, BUF_LEN, 0, (struct sockaddr*)&clientaddr, &clientaddrlen);
        
        //printed code, guess for codemaker
        printf("The code is: %s\n", code);
        printf("Guess received: %s\n", buffer);
        
        //prompts for feedback
        printf("Enter feedback: ");
        scanf("%4s", feedback);
        
        //send feedback to codebreaker over socket
        sendto(sockfd, feedback, BUF_LEN, 0, (struct sockaddr*)&clientaddr, clientaddrlen);
    }
    
    //codemaker close/shutdown created socket
    shutdown(sockfd, SHUT_RDWR);
    close(sockfd);
    return 0;
}

//If connecting to game
int do_codebreaker(char *ipaddr, in_port_t portno) {
    
    //Variables
    int sockfd;
    struct sockaddr_in serveraddr;
    socklen_t serveraddrlen = sizeof(serveraddr);
    char buffer[BUF_LEN];
    char receivedFeedback[BUF_LEN];
    
    //Creation of datagram socket
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    memset(&serveraddr, 0, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_port = htons(SERVER_PORT);
    serveraddr.sin_addr.s_addr = inet_addr(SERVER_ADDR);
    
    //Repeats feedback/prompts until feedback received is BBBB
    while (memcmp(receivedFeedback, "BBBB", 4) != 0) {
        
        //Prompt user for their guess
        memset(buffer, 0, BUF_LEN);
        printf("Enter guess: ");
        scanf("%4s", buffer);
        
        //Send guess to codemaker
        sendto(sockfd, buffer, BUF_LEN, 0, (struct sockaddr*)&serveraddr, sizeof(serveraddr));
        
        //Gets feedback and displays
        recvfrom(sockfd, receivedFeedback, BUF_LEN, 0, (struct sockaddr*)&serveraddr, &serveraddrlen);
        printf("Feedback: %s\n", receivedFeedback);
    
    }
    
    //Close socket
    close(sockfd);
    return 0;
}


//checks if port number is between 1024-65535
int checkPort(int arg) {
    if (arg >= 1024 && arg <= 65535) {
        return 1;
    }
    return 0;
}

//Checks create|connect/valid port no/right number of arguments. 
//Returns 0 if create, 1 if connect, 2 if error
int validateInput(int argc, char *argv[]) {
    //if user input is 'create', 3 arguments and if port number valid
    if ((memcmp(argv[1], "create", 6) == 0) && argc == 3) {
        if (checkPort(SERVER_PORT = atoi(argv[2])) == 1) {
            return 0;
        }
    } else if ((memcmp(argv[1], "connect", 7) == 0) && argc == 4) {
        if (checkPort(SERVER_PORT = atoi(argv[3])) == 1) {
            SERVER_ADDR = argv[2];
            return 1;
        }
    }
    
    return 2;
    
}

int main(int argc, char *argv[]) {

    //sets game mode/error
    int gameMode = validateInput(argc, argv);
    switch(gameMode) {
        case 0:
            printf("Creating game...\n");
            do_codemaker(SERVER_PORT);
            break;
        case 1:
            printf("Connecting to game...\n");
            do_codebreaker(SERVER_ADDR, SERVER_PORT);
            break;
        default:
            printf("Error in arguments...\n");
            return 0;
            break;
    }
    
    return 0;
    
}