/* Assignment 3 - Heap Blocks
   11/15/21
   Aditya Ramdas Chaudhari
   1001747134

*/
#include <assert.h>
#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>
#include <stdlib.h>

#define ALIGN4(s)         (((((s) - 1) >> 2) << 2) + 4)
#define BLOCK_DATA(b)      ((b) + 1)
#define BLOCK_HEADER(ptr)   ((struct _block *)(ptr) - 1)


static int atexit_registered = 0;
static int num_mallocs       = 0;
static int num_frees         = 0;
static int num_reuses        = 0;
static int num_grows         = 0;
static int num_splits        = 0;
static int num_coalesces     = 0;
static int num_blocks        = 0;
static int num_requested     = 0;
static int max_heap          = 0;

/*
 *  \brief printStatistics
 *
 *  \param none
 *
 *  Prints the heap statistics upon process exit.  Registered
 *  via atexit()
 *
 *  \return none
 */
void printStatistics( void )
{
  printf("\nheap management statistics\n");
  printf("mallocs:\t%d\n", num_mallocs );
  printf("frees:\t\t%d\n", num_frees );
  printf("reuses:\t\t%d\n", num_reuses );
  printf("grows:\t\t%d\n", num_grows );
  printf("splits:\t\t%d\n", num_splits );
  printf("coalesces:\t%d\n", num_coalesces );
  printf("blocks:\t\t%d\n", num_blocks );
  printf("requested:\t%d\n", num_requested );
  printf("max heap:\t%d\n", max_heap );
}

struct _block
{
   size_t  size;         /* Size of the allocated _block of memory in bytes */
   struct _block *prev;  /* Pointer to the previous _block of allcated memory   */
   struct _block *next;  /* Pointer to the next _block of allcated memory   */
   bool   free;          /* Is this _block free?                     */
   char   padding[3];
};


struct _block *heapList = NULL; /* Free list to track the _blocks available */
struct _block *prevU = NULL;//the memory block that was last used which is also used in next fit 
/*
 * \brief findFreeBlock
 *
 * \param last pointer to the linked list of free _blocks
 * \param size size of the _block needed in bytes
 *
 * \return a _block that fits the request or NULL if no free _block matches
 *
 * \TODO Implement Next Fit
 * \TODO Implement Best Fit
 * \TODO Implement Worst Fit
 */
struct _block *findFreeBlock(struct _block **last, size_t size)
{
   struct _block *curr = heapList;

#if defined FIT && FIT == 0
   /* First fit */
   while (curr && !(curr->free && curr->size >= size))
   {
      *last = curr;
      curr  = curr->next;
   }
#endif
//implementation of best fit 
#if defined BEST && BEST == 0
   /** \TODO Implement best fit here */
   struct _block *b = NULL;  //best fit memory block structure 
    //loop for best fit algorithm
   size_t bBlock=0;
    
   while(curr)
   {
      if((curr->free && curr->size>=size)&&(bBlock==0)){
         
         b=curr;
         bBlock=curr->size;
      }
      if((curr->free && curr->size>=size)&&curr->size < bBlock){
         b=curr;
         bBlock=b->size;
      }
      *last=curr;
      curr=curr->next;
   }
   curr=b;

#endif
//implementation of worst fit algorithm
#if defined WORST && WORST == 0
   /** \TODO Implement worst fit here */ 
   struct _block *w = NULL;  //best fit memory block structure 
    //loop for best fit algorithm
    size_t wBlock=0;
    
    while(curr)
    {
      if((curr->free && curr->size>=size)&&(wBlock==0)){
         if((curr->free && curr->size>=size)&&curr->size > wBlock){
            w=curr;
            wBlock=w->size;
         }
      curr=curr->next;
      }
   }
    curr=w;

#endif
//implementation of next fit 
#if defined NEXT && NEXT == 0
   /** \TODO Implement next fit here */
   if(prevU==NULL){
      curr=heapList;
   }
   if(curr){
      curr=prevU->next;
   }
   while(curr && !(curr->free && curr->size >= size))
   {
      *last=curr;
      curr = curr->next;
      prevU=curr;
   }
    //loop for next fit algorithm
#endif
return curr;
}

/*
 * \brief growheap
 *
 * Given a requested size of memory, use sbrk() to dynamically
 * increase the data segment of the calling process.  Updates
 * the free list with the newly allocated memory.
 *
 * \param last tail of the free _block list
 * \param size size in bytes to request from the OS
 *
 * \return returns the newly allocated _block of NULL if failed
 */
struct _block *growHeap(struct _block *last, size_t size)
{
   /* Request more space from OS */
   struct _block *curr = (struct _block *)sbrk(0);
   struct _block *prev = (struct _block *)sbrk(sizeof(struct _block) + size);

   assert(curr == prev);

   /* OS allocation failed */
   if (curr == (struct _block *)-1)
   {
      return NULL;
   }
   else
      num_blocks++;

   /* Update heapList if not set */
   if (heapList == NULL)
   {
      heapList = curr;
   }

   /* Attach new _block to prev _block */
   if (last)
   {
      last->next = curr;
   }
   num_blocks++;
   /* Update _block metadata */
   curr->size = size;
   curr->next = NULL;
   curr->free = false;
   max_heap+=size;
   return curr;
}

/*brief split free block 
function to split the free block if possible

*/
 void split_block(struct _block *b, size_t size)
{
   struct _block *temp=(struct _block*)sbrk(0); //New free block = block to be split-memory allocated for application
   temp->size=b->size-size-sizeof(struct _block); //locate new to the remaining data block area
//     //the original
//     //block of the split-allocate it The size of the memory-the size of the block structure itself new -> size = b -> size - size - BLOCK_SIZE;
    temp-> next = b -> next; //link list insert
    temp-> free =  true ; //free mark available
    b-> size = size;
    b-> next =  temp ; //link list insertion
    num_splits++;
    num_blocks++;
}
// /*brief merge block 
// function to merge the block

// */
// struct _block merge_block(struct _block b){
//     if(b->next && b->next->free){
//         b->size+=12+b->next->size;
//         b->next=b->next->next;
//         if(b->next){
//             b->next->prev=b;
//         }
//     }
//     return b;
// }
/*
 * \brief malloc
 *
 * finds a free _block of heap memory for the calling process.
 * if there is no free _block that satisfies the request then grows the
 * heap and returns a new _block
 *
 * \param size size of the requested memory in bytes
 *
 * \return returns the requested memory allocation to the calling process
 * or NULL if failed
 */
void *malloc(size_t size)
{

   if( atexit_registered == 0 )
   {
      atexit_registered = 1;
      atexit( printStatistics );
   }

   /* Align to multiple of 4 */
   size = ALIGN4(size);

   /* Handle 0 size */
   if (size == 0)
   {
      return NULL;
   }

   /* Look for free _block */
   struct _block *last = heapList;
   struct _block *next = findFreeBlock(&last, size);

   /* TODO: Split free _block if possible */
   //implementing the split free block if possible by using split block function
   if(next && (next->size-size>sizeof(struct _block))){
   split_block(next,size);
   next->free=false;
   num_reuses++;
   }
  
   /* Could not find free _block, so grow heap */
   if (next == NULL)
   {
      next = growHeap(last, size);
      num_grows++;
      num_blocks++;
   }
   else{
      num_reuses++;
   }

   /* Could not find free _block or grow heap, so just return NULL */
   if (next == NULL)
   {
      return NULL;
   }

   /* Mark _block as in use */
   next->free = false;
   num_mallocs++;

   /* Return data address associated with _block */
   return BLOCK_DATA(next);
}
/*
 * \brief calloc
 *allocating the multiple blocks of memory. It is a dynamic memory 
 allocation function which is used to allocate the memory to 
 complex data structures such as arrays and structures.
 * \param size size of the requested memory in bytes
 *
 * \return returns the requested memory allocation to the calling process
 * or NULL if failed
 */

void *calloc(size_t nmemb, size_t size)
{
    num_requested+=nmemb * size;
    size_t * new1 ;
    size_t l, m;
    new1 = malloc (nmemb * size);
     if ( new1 )
    {
        l = ALIGN4(nmemb * size) >> 2 ;
         for (m = 0 ; m <l; ++m)
             new1 [ m] = 0 ;
    }
    return  new1 ;

}
/*
 * \brief realloc
 *resizing the block of memory that was previously allocated. 
 The realloc function allocates a block of memory 
 which can make it larger or smaller in size than the original
and copies the contents of the old block to the new block of 
memory, if necessary.
 * \param size size of the requested memory in bytes
 *
 * \return returns the requested memory allocation to the calling process
 * or NULL if failed
 */
void *realloc(void *p, size_t size)
{
    void *destination=malloc(size);
    memcpy(destination,p,BLOCK_HEADER(p)->size);
    free(p);
    return destination;
}

/*
 * \brief coalescing
  freeing the allocated heap block  passing a pointer to the block to free.
  Coalescing here to reuse freed space as part of a future block of a larger size.
 *
 * \param ptr the heap memory to free
 *
 * \return none
 */
 /*
 * \brief free
 *
 * frees the memory _block pointed to by pointer. if the _block is adjacent
 * to another _block then coalesces (combines) them
 *
 * \param ptr the heap memory to free
 *
 * \return none
 */
void free(void *ptr)
{
   if (ptr == NULL)
   {
      return;
   }

   /* Make _block as free */
   struct _block *curr = BLOCK_HEADER(ptr);
   assert(curr->free == 0);
   curr->free = true;

   /* TODO: Coalesce free _blocks if needed */
   struct _block *coalesce=heapList;
   while(coalesce!=NULL && coalesce->next!=NULL){
    if(coalesce->free && coalesce->next->free){
        coalesce->next=coalesce->next->next;
        coalesce->size+=coalesce->next->size+sizeof(struct _block);
        num_coalesces++;
        num_blocks--;
      }
   curr=curr->next;
   }
   num_frees++;
}

/* 7f704d5f-9811-4b91-a918-57c1bb646b70       --------------------------------*/
/* vim: set expandtab sts=3 sw=3 ts=6 ft=cpp: --------------------------------*/
