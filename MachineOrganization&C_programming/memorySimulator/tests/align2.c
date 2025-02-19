/* a few allocations in multiples of 4 bytes checked for alignment */
#include <assert.h>
#include <stdlib.h>
#include <stdint.h>
#include "mem.h"

int main() {
   assert(Mem_Init(4096) == 0);
   Mem_Dump();
   int* ptr[4];

   ptr[0] = (int*) Mem_Alloc(4);
   Mem_Dump();
   ptr[1] = (int*) Mem_Alloc(8);
   Mem_Dump();
   ptr[2] = (int*) Mem_Alloc(16);
   Mem_Dump();
   ptr[3] = (int*) Mem_Alloc(4);
   Mem_Dump();

   assert((int)(ptr[0]) % 4 == 0);
   assert((int)(ptr[1]) % 4 == 0);
   assert((int)(ptr[2]) % 4 == 0);
   assert((int)(ptr[3]) % 4 == 0);

   exit(0);
}
