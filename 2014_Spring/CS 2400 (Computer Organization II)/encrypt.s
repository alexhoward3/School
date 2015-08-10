	; Author: Alex Howard
	; encrypt.s
	; Homework 6
	; March 2014

	; This program encrypts and decrypts a string using XOR logic
	; with the use of saving to memory and using a stack.
	; The string it encrypts/decrypts MUST be a multiple of 4 due
	; to the byte permutation: 4 gets 1, 1 gets 4, 2 gets 3, 3 gets 2
	; and the store to memory/stack logic.

	; r0 = Output
	; r1 = String
	; r2 = String Copy
	; r3 = First byte
	; r4 = Second byte
	; r5 = Third byte
	; r6 = Fourth byte
	; r7 = Key
	; r8 = Compare

	AREA encrypt, CODE, READONLY

SWI_WriteC	EQU	&0
SWI_Exit	EQU	&11

	ENTRY

	ldr		r7, key
	bl		Print
	bl		Encrypt		; Encrypt
	bl		Decrypt		; Decrypt
	swi		SWI_Exit	; Exit

Encrypt
		adr		r1, STR			; Load String into r1
		mov		r2, r1			; Copy String to r2
elp		ldrb	r3, [r2], #1	; Load first byte to r3
		mov		r3, r3, LSL #24	; Shift left 3 bytes
		ldrb	r4, [r2], #1	; Load second byte to r4
		mov		r4, r4, LSL #16	; Shift left by 2 bytes
		ldrb	r5, [r2], #1	; Load third byte to r5
		mov		r5, r5, LSL #8	; Shift left 3 bytes
		ldrb	r6, [r2], #1	; Load fourth byte

		orr		r3, r3, r4		; ORR r3, r4. Store in r3
		orr		r3, r3, r5		; ORR r3, r5. Store in r3
		orr		r3, r3, r6		; ORR r3, r6. Store in r3

		eor		r3, r3, r7		; Exclusive OR with key. Store in r3

		; If not last byte save registers to stack
		sub		sp, sp, #4		; Move to next stack location
		str		r3, [sp, #0]	; Save r3 to stack
		; Load Encrypted String into memory overwriting original string
		str		r3, [r1], #4	; Store the encrypted bytes back into memory

		ldr		r8, [r2]		; Load the last byte into r8
		cmp		r8, #0			; Check if it is 0
		add		r10, r10, #1	; Add 1 to incrementer
		bne		elp				; Loop if not on last byte

		mov		r9, lr			; Save Link Register
		adr		r1, STR
		bl		Print			; Print

		mov		lr, r9			; Restore Link Register
		mov		pc, lr			; RETURN

Decrypt
		adr		r1, STR			; Load STR into r1
		mov		r2, #3			; Move 3 into r2
		mov		r8, r10			; Save the incrementer to r8
		mul		r10, r2, r10	; Multiply to get the Stack counter * offset
		add		r1, r1, r10		; Move to end of memory offset
dlp		ldr		r2, [sp]		; Pop stack, save to r3
		eor		r2, r2, r7		; Exclusive OR back to original String
		str		r2, [sp]

		ldrb	r3, [sp], #1	; Load first byte to r3
		mov		r3, r3, LSL #24	; Shift left 3 bytes
		ldrb	r4, [sp], #1	; Load second byte to r4
		mov		r4, r4, LSL #16	; Shift left by 2 bytes
		ldrb	r5, [sp], #1	; Load third byte to r5
		mov		r5, r5, LSL #8	; Shift left 1 byte
		ldrb	r6, [sp], #1	; Load fourth byte

		orr		r3, r3, r4		; ORR r3, r4. Store in r3
		orr		r3, r3, r5		; ORR r3, r5. Store in r3
		orr		r3, r3, r6		; ORR r3, r6. Store in r3
		; Go to the end of memory and save the string in reverse order to be printed
		str		r3, [r1]		; Store the Decrypted word to memory (end of memory)
		sub		r1, r1, #4		; Move down to next location in memory
		sub		r8, r8, #1		; Decrement the stack counter
		cmp		r8, #0			; Compare r8 to 0
		bne		dlp				; Loop if not equal

		mov		r9, lr			; Save link register
		bl		Print			; Print the string

		mov		lr, r9			; Replace the Link Register
		mov		pc, lr			; RETURN



Print
			adr		r1, STR			; Load the string into memory (beginning of memory)
loop		ldrb	r0, [r1], #1	; Put character in r0, increment to next byte in COUNTS
			cmp		r0, #0			; Is the character NUL (endline?)
			swine	SWI_WriteC		; If not NUL, print
			bne		loop			; Loop Count subroutine
			mov		r0, #10			; Carriage return
			swi		SWI_WriteC		; Write carriage return
			add		r14, r14, #3	; Pass next word boundary
			bic		r14, r14, #3	; Round back to boundary
			mov		pc, lr			; Return


STR DCB "ALEXfredMARYjane",0		; String
key	DCD 0x34251239		; Key
	END
