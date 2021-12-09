#define _GNU_SOURCE
#include <stdio.h>
#include <unistd.h>
#include <stdint.h>
#include <errno.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>

#define WHITESPACE " \t\n"      // We want to split our command line up into tokens
                                // so we need to define what delimits our tokens.
                                // In this case  white space
                                // will separate the tokens on our command line

#define MAX_COMMAND_SIZE 255    // The maximum command-line size

#define MAX_NUM_ARGUMENTS 5     // Mav shell only supports five arguments
#define NUM_BLOCKS 4226
#define NUM_FILES  134
#define MAX_FILE_SIZE 10165879
#define BLOCK_SIZE 8192



FILE *file_handler;
uint8_t blocks[NUM_BLOCKS][BLOCK_SIZE];
uint8_t *free_inodes_list; 
uint8_t *free_blocks_list; 

struct Inode{
  uint8_t valid;
  uint8_t attributes;
  uint32_t size;
  uint32_t blocks[1250];
}*inodes_struct;
struct Directory_Entry{
  uint8_t valid;
  char filename[32];
  uint32_t inode;
}*directory_struct;


void initializeInodeList(){
  int iterator; 
  for (iterator = 0; iterator <NUM_BLOCKS; iterator++){
    free_inodes_list[iterator] = 1; 
  }
}
void initializeInodes(){
  int iterator; 
  int nested_iterator; 
  for (iterator = 0; iterator < NUM_FILES; iterator++){
    inodes_struct[iterator].size     = 0; 
    inodes_struct[iterator].valid    = 0; 
    inodes_struct[iterator].attributes = 0;    
    for ( nested_iterator = 0; nested_iterator < 1250; nested_iterator++){
      inodes_struct[iterator].blocks[nested_iterator] = -1;
    }

  }
}
int findFreeInode(){
  int iterator; 
  int result = -1; 
  for (iterator = 0; iterator < NUM_FILES; iterator++){
    if (!inodes_struct[iterator].valid){
      inodes_struct[iterator].valid = 1;
      return iterator; 
    }
  }
  return result; 
}

void initializeDirectory(){
  int iterator; 
  for (iterator = 0; iterator < NUM_FILES; iterator++){
    directory_struct[iterator].inode = -1;    
    directory_struct[iterator].valid = 0; 
    memset(directory_struct[iterator].filename, 0, 32);
  }
}
int findDirectoryEntry(char *filename){
  int iterator; 
 
  for (iterator = 0; iterator <NUM_FILES; iterator++){
    if ( strcmp(filename, directory_struct[iterator].filename) == 0)
      return iterator; 
  }

  for (iterator = 0; iterator < NUM_FILES; iterator++){
    if (directory_struct[iterator].valid == 0){
      directory_struct[iterator].valid = 1;
      return iterator; 
    }
  }
  return -1; 
}

void initializeBlockList(){
  int iterator; 
  for (iterator = 0; iterator < NUM_BLOCKS; iterator++){
    free_blocks_list[iterator] = 1; 
  }
} 
int findFreeBlock(){
  int iterator; 

  for (iterator = 10; iterator < NUM_BLOCKS; iterator++){
    if (free_blocks_list[iterator]){
      free_blocks_list[iterator] = 0; 
      return iterator; 
    }
  }
  return -1; 
}

int df(){
  int iterator; 
  int free_space = 0;
  for (iterator = 0; iterator <NUM_BLOCKS; iterator++){
    if (free_blocks_list[iterator])
      free_space+= BLOCK_SIZE;
  }
  return free_space;
}
int open(char * filename)
{
  file_handler = fopen(filename, "r");
  if (file_handler == NULL){
    perror("File not found ");
    return -1;
  }
  else{
    fread(&blocks[0], NUM_BLOCKS, BLOCK_SIZE, file_handler);
  }
  return 0;
}
int closes(char * filename){
  if(filename == NULL){
    return -1;
  }

  file_handler = fopen(filename, "w");
  if (file_handler == NULL){
    perror("Error opening file ");
    return -1;
  }
  else{
    fwrite(&blocks[0], NUM_BLOCKS, BLOCK_SIZE, file_handler);
    fclose(file_handler);
    file_handler = NULL;
    return 0;
  }

int put( char * filename ){

  struct stat buffer; 
  int result; 
  int offset = 0;
  result = stat(filename, &buffer);
  int size = buffer.st_size; 
  if(strlen(filename)>32 ){
    printf("File name too long \n");
    return -1;
  }
  if (result == -1){
    printf("File does not exist\n");
    return -1; 
  }
  if ( size > df() ) 
  {
    printf("Not enough disk space\n");
    return -1;
  }
  if ( size > MAX_FILE_SIZE ) 
  {
    printf("File size too large to allocate\n");
    return -1;
  }
  int directory_index = findDirectoryEntry(filename);
  int free_block = findFreeBlock();
  int inode_index = findFreeInode();

  memcpy(directory_struct[directory_index].filename, filename, strlen(filename));
  file_handler = fopen(filename, "r");
  printf("Reading %d bytes from %s\n", size, filename );
  while (size > 0)
  {
    fseek(file_handler, offset,  SEEK_SET );

    int bytes = fread(blocks[free_block], BLOCK_SIZE, 1, file_handler);

    if ( bytes == 0 && !feof(file_handler) )
    {
      printf("\nAn error occured\n");
      return -1;
    }
    clearerr(file_handler); 
    size -= BLOCK_SIZE;  
    offset += BLOCK_SIZE; 
    free_block++ ;
  }
  fclose(file_handler);
  return 0;
}


int get (char *filename, char *newfilename)
{
  struct stat buffer;
  int iterator;
  int status;
  int offset = 0;
  status = stat(filename, &buffer ); 
  int size = buffer.st_size;          
  int directory_index = findDirectoryEntry(newfilename);
  int inode_index = findFreeInode();
  int free_block = findFreeBlock();
  int block_index = 0;
  if (status == -1) {   
    printf("File %s not found\n", filename);
    return -1;
  }
  if (file_handler == NULL){
    printf("Could not open file: %s\n", newfilename);
    return -1;
  }
  for (iterator = 0; iterator < NUM_FILES; iterator++){
    if ( strcmp(filename, directory_struct[iterator].filename) == 0){
      file_handler = fopen(newfilename, "w");
      printf("Writing %d bytes from %s to %s\n", size, filename, newfilename);
      while ( size > 0){
        int num_bytes;
        if (size < BLOCK_SIZE){
          num_bytes = size;
        }
        else{
          num_bytes = BLOCK_SIZE;
        }
        fwrite(blocks[block_index], num_bytes, 1, file_handler);
        size -= BLOCK_SIZE;
        block_index++; 
        offset += BLOCK_SIZE;
        
        fseek(file_handler, offset, SEEK_SET);
      }
      fclose(file_handler);
      return 0;
    }
  }
  printf("error: File not found\n");
  return -1;
}



void list()
{
  int iterator; 
  if (directory_struct->valid == 0) 
  {
    printf("No files found\n");
    
  }
  for ( iterator = 0; iterator < NUM_FILES; iterator++)
  {
    if (directory_struct[iterator].valid == 1)
    {
      struct stat buffer; 
      int result; 
      result = stat(directory_struct[iterator].filename, &buffer);
      int size = buffer.st_size; 
      time_t val = buffer.st_mtime;
      int inode_index = directory_struct[iterator].inode;
      if (size != 0 && (inodes_struct[result].attributes == 0 || inodes_struct[result].attributes == 20)) {
        printf(" %s %d %s \n", directory_struct[iterator].filename, size, asctime(localtime(&buffer.st_mtime)));
      }
      if (size == 0){
        printf("%s has been deleted \n", directory_struct[iterator].filename);
      }
    }
  }
}

}
void createfs(char *filename){
  file_handler = fopen(filename, "w");
  memset( &blocks[0], 0, NUM_BLOCKS *BLOCK_SIZE);
  initializeDirectory(); 
  initializeBlockList();
  initializeInodeList();
  initializeInodes();
  fwrite( &blocks[0], NUM_BLOCKS, BLOCK_SIZE, file_handler);
  fclose(file_handler);
}

int attrib(char *attrib, char *filename){
  struct stat buffer;
  int status;
  status = stat(filename, &buffer ); 

  if (status == -1){
    printf("File not found\n");
    return -1;
  }

  if (!strcmp(attrib, "+h")){
    inodes_struct[status].attributes += 10;
    return 1; 
  }

  if (!strcmp(attrib, "-h")){
    if (inodes_struct[status].attributes == 10){
      inodes_struct[status].attributes -= 10;  
    }
    return 1;
  }

  if (!strcmp(attrib, "+r")){
    inodes_struct[status].attributes += 20;
    return 1;
  }

  if (!strcmp(attrib, "-r")){
    if (inodes_struct[status].attributes == 20){
    inodes_struct[status].attributes -= 20; 
    } 
    return 1;
  }
  return 1; 
}

int main()
{

  char * cmd_str = (char*) malloc( MAX_COMMAND_SIZE );
  directory_struct = (struct Directory_Entry *) &blocks[0];
  inodes_struct = (struct Inode *)&blocks[3];
  free_inodes_list = (uint8_t *)&blocks[1];
  free_blocks_list = (uint8_t *)&blocks[2];
  int h = 0;
  int r = 0;
  char hidden[NUM_FILES+1][MAX_COMMAND_SIZE];
  char read_only[NUM_FILES+1][MAX_COMMAND_SIZE];

  initializeDirectory();
  initializeBlockList();
  initializeInodeList();
  initializeInodes();

  while( 1 )
  {
    // Print out the mfs prompt
    printf ("mfs> ");

    // Read the command from the commandline.  The
    // maximum command that will be read is MAX_COMMAND_SIZE
    // This while command will wait here until the user
    // inputs something since fgets returns NULL when there
    // is no input
    while( !fgets (cmd_str, MAX_COMMAND_SIZE, stdin) );

    /* Parse input */
    char *token[MAX_NUM_ARGUMENTS];

    int   token_count = 0;                                 
                                                           
    // Pointer to point to the token
    // parsed by strsep
    char *arg_ptr;                                         
                                                           
    char *working_str  = strdup( cmd_str );                

    // we are going to move the working_str pointer so
    // keep track of its original value so we can deallocate
    // the correct amount at the end
    char *working_root = working_str;

    // Tokenize the input stringswith whitespace used as the delimiter
    while ( ( (arg_ptr = strsep(&working_str, WHITESPACE ) ) != NULL) && 
              (token_count<MAX_NUM_ARGUMENTS))
    {
      token[token_count] = strndup( arg_ptr, MAX_COMMAND_SIZE );
      if( strlen( token[token_count] ) == 0 )
      {
        token[token_count] = NULL;
      }
        token_count++;
    }

    // Now print the tokenized input as a debug check
    // \TODO Remove this code and replace with your shell functionality

    if (token[0] != NULL)
    {
      if( !strcmp(token[0], "quit") || !strcmp(token[0], "Quit") || !strcmp(token[0], "Exit") || !strcmp(token[0], "exit") || !strcmp(token[0], "Q") || !strcmp(token[0], "q")){
        return 0;
      }
      else if( !strcmp(token[0], "put") || !strcmp(token[0], "Put")){
        put(token[1]);
      }
      else if( !strcmp(token[0], "get") || !strcmp(token[0], "Get")){
        if (token[2] == NULL){
          get(token[1], token[1]);
        }
        else{
          get(token[1],token[2]);
        }
      }
      else if (!strcmp (token[0], "del")){
        struct stat xyz; 
        int retxyz; 
        retxyz = stat(token[1], &xyz);
        if (inodes_struct[retxyz].attributes == 20 || inodes_struct[retxyz].attributes == 30){
          printf("File is read-only, can not be deleted \n");
        }
        else{
            int try; 
            try = remove(token[1]);
            if(try == 0) {
                printf("File deleted successfully \n");
                struct stat del; 
                int deleted; 
                deleted = stat(token[1], &del);
                int size_del = del.st_size; 
                size_del -= BLOCK_SIZE ; 
            }
            else{
                printf("Error: unable to delete the file \n");
            }
        }
    }
    else if (!strcmp (token[0], "list")) {
        list();
    }
    else if (!strcmp (token[0], "df") || !strcmp(token[0], "DF")) {
        printf("Disk Space remaining %d bytes\n", df());
    }
    else if (!strcmp (token[0], "open")){
        file_handler = fopen(token[1], "r");
        if (file_handler == NULL){
          perror("File not found ");
        }
        else{
          fread(&blocks[0], NUM_BLOCKS, BLOCK_SIZE, file_handler);
        }
    }
    else if (!(strcmp(token[0], "close"))){
        if(token[1] == NULL){
            printf("close: File not found. \n");
        }
        closes(token[1]);
    }
    else if (!(strcmp("attrib", token[0]))){
        attrib(token[1], token[2]);
    }
    else if (!(strcmp("createfs", token[0]))){
        if (token[1] == 0){
          printf("createfs: File not found. \n");
        }
        else{
          createfs(token[1]);
          printf("Created new disk image called %s\n", token[1]);
        }
    }
    else{
        printf("Command not recognized. Try again. \n");
    }
    }
    free( working_root );
  }
  return 0;
}