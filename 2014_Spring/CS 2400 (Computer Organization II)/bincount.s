		; Author: Alex Howard
		; bincount.s
		;
		; March 2014
		; This program generates a random number and counts the number
		; of 1's and 0's that number would have as a binary number.

		; r0 = Output
		; r1 = NUM
		; r2 = Comparison
		; r3 = 1 counter
		; r4 = 0 counter
		; r5 = Count
		; r6 = Storage
		; r13 = Save link register


		AREA bincount, CODE, READONLY

SWI_WriteC	EQU &0
SWI_Exit	EQU &11

		ENTRY
		IMPORT	randomnumber
		IMPORT	seed
		bl	generate	; Generate a random number using RANDOM.S
		mov	r1, r0		; Move number into r1
		bl	Print		; Print the number
		bl 	one			; Branch and link to the one counter subroutine
		bl 	zero		; Branch and link to the two counter subroutine
		bl	Count1		; Print 1 Count :
		bl	print1		; Print 1's
		bl	Count0		; Print 0 Count :
		bl	print0		; Print 0's

		bl	generate	; Generate a random number using RANDOM.S
		mov	r1, r0		; Move number into r1
		bl	Print		; Print the number
		bl 	one			; Branch and link to the one counter subroutine
		bl 	zero		; Branch and link to the two counter subroutine
		bl	Count1		; Print 1 Count :
		bl	print1		; Print 1's
		bl	Count0		; Print 0 Count :
		bl	print0		; Print 0's

		bl	generate	; Generate a random number using RANDOM.S
		mov	r1, r0		; Move number into r1
		bl	Print		; Print the number
		bl 	one			; Branch and link to the one counter subroutine
		bl 	zero		; Branch and link to the two counter subroutine
		bl	Count1		; Print 1 Count :
		bl	print1		; Print 1's
		bl	Count0		; Print 0 Count :
		bl	print0		; Print 0's

		bl	generate	; Generate a random number using RANDOM.S
		mov	r1, r0		; Move number into r1
		bl	Print		; Print the number
		bl 	one			; Branch and link to the one counter subroutine
		bl 	zero		; Branch and link to the two counter subroutine
		bl	Count1		; Print 1 Count :
		bl	print1		; Print 1's
		bl	Count0		; Print 0 Count :
		bl	print0		; Print 0's

		bl	generate	; Generate a random number using RANDOM.S
		mov	r1, r0		; Move number into r1
		bl	Print		; Print the number
		bl 	one			; Branch and link to the one counter subroutine
		bl 	zero		; Branch and link to the two counter subroutine
		bl	Count1		; Print 1 Count :
		bl	print1		; Print 1's
		bl	Count0		; Print 0 Count :
		bl	print0		; Print 0's



		swi	SWI_Exit	; Exit


generate
		mov		r5, #10			; Start counter at 10
		mov		r13, lr			; Save link register
		bl		randomnumber	; Branch to randomnumber generator subroutine
		mov		lr, r13			; Return r13, to link register
		mov		pc, lr			; Return

Print
			mov 	r6, r1			; Save NUM into r6
			mov		r2, #8			; Count of nibbles = 8
pl			mov		r0, r6, LSR #28	; Get top nibble
			cmp		r0, #9			; Hexanumber 0-9 or A-F
			addgt	r0, r0, #"A"-10	; ASCII alphabetic
			addle	r0, r0, #"0"	; ASCII numeric
			swi		SWI_WriteC		; Print
			mov		r6, r6, LSL #4	; Shift left one nibble
			subs	r2, r2, #1		; Decrement nibble count
			bne		pl				; If more nibbles, loop
			mov		r0, #10			; Output gets carriage return
			swi		SWI_WriteC		; Write carriage return
			mov		pc, r14			; Return


one
		mov		r6, r1			; Save NUM into r6
		mov		r5, #8			; Nibble counter
oloop	mov		r2, r6, LSR #28	; Load first byte into r2

			; Compare for one 1's
		cmp		r2, #1			; Compare with hex 0001
		addeq	r3, r3, #1		; Add 1 to r3 (1 counter)
		cmp		r2, #2			; Compare with hex 0010
		addeq	r3, r3, #1		; Add 1 to r3 (1 counter)
		cmp		r2, #4			; Compare with hex 0100
		addeq	r3, r3, #1		; Add 1 to r3 (1 counter)
		cmp 	r2, #8			; Compare with hex 1000
		addeq	r3, r3, #1		; Add 1 to r3 (1 counter)

			; Compare for two 1's
		cmp		r2, #5			; Compare with hex 0101
		addeq	r3, r3, #2		; Add 2 to r3 (1 counter)
		cmp		r2, #6			; Compare with hex 0110
		addeq	r3, r3, #2		; Add 2 to r3 (1 counter)
		cmp		r2, #3			; Compare with hex 0011
		addeq	r3, r3, #2		; Add 1 to r3 (1 counter)
		cmp		r2, #9			; Compare with hex 1001
		addeq	r3, r3, #2		; Add 2 to r3 (1 counter)
		cmp		r2, #0xA		; Compare with hex 1010
		addeq	r3, r3, #2		; Add 2 to r3 (1 counter)
		cmp		r2, #0xC		; Compare with hex 1100
		addeq	r3, r3, #2		; Add 2 to r3 (1 counter)

			; Compare for three 1's
		cmp		r2, #7			; Compare with hex 0111
		addeq	r3, r3, #3		; Add 3 to r3 (1 counter)
		cmp		r2, #0xB		; Compare with hex 1011
		addeq	r3, r3, #3		; Add 3 to r3 (1 counter)
		cmp		r2, #0xD		; Compare with hex 1101
		addeq	r3, r3, #3		; Add 3 to r3 (1 counter)
		cmp		r2, #0xE		; Compare with hex 1110
		addeq	r3, r3, #3		; Add 3 to r3 (1 counter)

			; Compare for four 1's
		cmp		r2, #0xF		; Compare with hex 1111
		addeq	r3, r3, #4		; Add 4 to r3 (1 counter)

		mov		r6, r6, LSL #4	; Shift left one nibble
		subs	r5, r5, #1		; Decrement nibble counter
		bne		oloop			; Loop
		mov	pc, lr				; Return


zero
		mov		r6, r1			; Save NUM into r6
		mov		r5, #8			; Nibble counter
zloop	mov		r2, r6, LSR #28	; Load first byte into r2

			; Compare for one 0's
		cmp		r2, #7			; Compare with hex 0111
		addeq	r4, r4, #1		; Add 1 to r4 (0 counter)
		cmp		r2, #0xB		; Compare with hex 1011
		addeq	r4, r4, #1		; Add 1 to r4 (0 counter)
		cmp		r2, #0xD		; Compare with hex 1101
		addeq	r4, r4, #1		; Add 1 to r4 (0 counter)
		cmp		r2, #0xE		; Compare with hex 1110
		addeq	r4, r4, #1		; Add 1 to r4 (0 counter)

			; Compare for two 0's
		cmp		r2, #3			; Compare with hex 0011
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)
		cmp		r2, #5			; Compare with hex 0101
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)
		cmp		r2, #6			; Compare with hex 0110
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)
		cmp		r2, #9			; Compare with hex 1001
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)
		cmp		r2, #0xA		; Compare with hex 1010
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)
		cmp		r2, #0xC		; Compare with hex 1100
		addeq	r4, r4, #2		; Add 2 to r4 (0 counter)

			; Compare for three 0's
		cmp		r2, #1			; Compare with hex 0001
		addeq	r4, r4, #3		; Add 3 to r4 (0 counter)
		cmp		r2, #2			; Compare with hex 0010
		addeq	r4, r4, #3		; Add 3 to r4 (0 counter)
		cmp		r2, #4			; Compare with hex 0100
		addeq	r4, r4, #3		; Add 3 to r4 (0 counter)
		cmp 	r2, #8			; Compare with hex 1000
		addeq	r4, r4, #3		; Add 3 to r4 (0 counter)

			; Compare for four 0's
		cmp		r2, #0			; Compare with hex 0000
		addeq	r4, r4, #4		; Add 4 to r4 (0 counter)

		mov		r6, r6, LSL #4	; Shift left one nibble
		subs	r5, r5, #1		; Decrement nibble counter
		bne		zloop			; Loop
		mov	pc, lr				; Return


Count1
			adr		r6, onestr		; 1's count
c1lp		ldrb	r0, [r6], #1	; Put character in r0, increment to next byte in COUNTS
			cmp		r0, #0			; Is the character NUL (endline?)
			swine	SWI_WriteC		; If not NUL, print
			bne		c1lp			; Loop Count subroutine
			add		r14, r14, #3	; Pass next word boundary
			bic		r14, r14, #3	; Round back to boundary
			mov		pc, lr			; Return


print1		mov		r5, #8			; Count of nibbles = 8
lp1			mov		r0, r3, LSR #28	; Get top nibble
			cmp		r0, #9			; Hexanumber 0-9 or A-F
			addgt	r0, r0, #"A"-10	; ASCII alphabetic
			addle	r0, r0, #"0"	; ASCII numeric
			swi		SWI_WriteC		; Print
			mov		r3, r3, LSL #4	; Shift left one nibble
			subs	r5, r5, #1		; Decrement nibble count
			bne		lp1				; If more nibbles, loop
			mov		r0, #10			; Carriage return
			swi		SWI_WriteC		; Print carriage return
			mov		pc, lr			; Return

Count0
			adr		r6, zerostr		; 0's count
c0lp		ldrb	r0, [r6], #1	; Put character in r0, increment to next byte in COUNTS
			cmp		r0, #0			; Is the character NUL (endline?)
			swine	SWI_WriteC		; If not NUL, print
			bne		c0lp			; Loop Count subroutine
			add		r14, r14, #3	; Pass next word boundary
			bic		r14, r14, #3	; Round back to boundary
			mov		pc, lr			; Return


print0		mov		r5, #8			; Count of nibbles = 8
lp0			mov		r0, r4, LSR #28	; Get top nibble
			cmp		r0, #9			; Hexanumber 0-9 or A-F
			addgt	r0, r0, #"A"-10	; ASCII alphabetic
			addle	r0, r0, #"0"	; ASCII numeric
			swi		SWI_WriteC		; Print
			mov		r4, r4, LSL #4	; Shift left one nibble
			subs	r5, r5, #1		; Decrement nibble count
			bne		lp0				; If more nibbles, loop
			mov		r0, #10			; Carriage return
			swi		SWI_WriteC		; Print carriage return
			mov		pc, r14			; Return


NUM 	DCD	0x170F173F
onestr	DCB	"1's Count: ",0
zerostr	DCB "0's Count: ",0

		END
