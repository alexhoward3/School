/*
 * hello.s
 *
 *  Created on: Mar 2, 2014
 *      Author: Alex Howard
 */

	AREA hello, CODE, READONLY

	ENTRY
SWI_Exit EQU &11

	mov r0, #5
	mov r1, #10
	add r0, r5, #10
	swi SWI_Exit

	END
