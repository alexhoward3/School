	;******************************
	; File : vowels.s
	; @author Alex Howard
	; This program analyzes a string of characters for the count of vowels.
	; If the vowel in the string is lowercase, the program converts the vowel to
	; uppercase and puts that byte back into memory where the string is located.
	; The program then prints the modified string and the count of the values in hexadecimal.
	; Date: February 2014
	;******************************

	; r0 = PRINT
	; r1 = COUNT
	; r2 = REPLACE
	; r3 = CONVERT UPPERCASE
	; r5 = STRING
	; r6 = COUNTS

	AREA vowels, CODE, READONLY
SWI_WriteC	EQU	&0					; Output character in r0
SWI_Exit	EQU	&11					; Exit program

			ENTRY

			mov		r1, #0			; r0 gets 0
			adr		r6, COUNTS		; r6 gets COUNTS

			adr		r5, STRING		; r5 gets STRING
			bl 		Print			; Call Print subroutine

			adr		r5, STRING		; r5 gets STRING
			bl 		Parse			; Call Count subroutine

			adr		r5, STRING		; r5 gets STRING
			bl		Print			; Call Print subroutine

			bl		Count			; Call the Count subroutine
			bl		Hex				; Call Print subroutine

			swi		SWI_Exit		; Exit

Print
			ldrb 	r0, [r5], #1	; Put character in r0, increment to the next byte in STRING
			cmp		r0, #0			; Is the character NUL (endline?)
			swine	SWI_WriteC		; If not NUL, print
			bne		Print			; Loop to Print subroutine
			add		r14, r14, #3	; Pass next word boundary
			bic		r14, r14, #3	; Round back to boundary
			mov		pc, lr			; Return

Parse
			ldrb	r0, [r5]		; Put character in r0
			cmp		r0, #&61		; Is the character "a"?
			cmpne	r0, #&65		; Is the character "e"?
			cmpne	r0, #&69		; Is the character "i"?
			cmpne	r0, #&6F		; Is the character "o"?
			cmpne	r0, #&75		; Is the character "u"?
			subeq	r0, r0, #&20	; Convert to uppercase, store in r3
			cmpne	r0, #&41		; Is the character "A"?
			cmpne	r0, #&45		; Is the character "E"?
			cmpne	r0, #&49		; Is the character "I"?
			cmpne	r0, #&4F		; Is the character "O"?
			cmpne	r0, #&55		; Is the character "U"?
			strb	r0, [r5]		; Rewrite in r0 back into STRING (if lowecase vowel, changes to uppercase)
			addeq 	r1, r1, #1		; Increment the counter
			add		r5, r5, #1		; Increment to the next byte in STRING
			cmp		r0, #0			; Is the character NUL (endline?)
			bne		Parse			; Loop to Parse subroutine
			mov		pc, lr			; Return

Count
			ldrb	r0, [r6], #1	; Put character in r0, increment to next byte in COUNTS
			cmp		r0, #0			; Is the character NUL (endline?)
			swine	SWI_WriteC		; If not NUL, print
			bne		Count			; Loop Count subroutine
			add		r14, r14, #3	; Pass next word boundary
			bic		r14, r14, #3	; Round back to boundary
			mov		pc, lr			; Return

Hex
			mov		r2, #8			; Count of nibbles = 8
HEXLOOP		mov		r0, r1, LSR #28	; Get top nibble
			cmp		r0, #9			; Hexanumber 0-9 or A-F
			addgt	r0, r0, #"A"-10	; ASCII alphabetic
			addle	r0, r0, #"0"	; ASCII numeric
			swi		SWI_WriteC		; Print
			mov		r1, r1, LSL #4	; Shift left one nibble
			subs	r2, r2, #1		; Decrement nibble count
			bne		HEXLOOP			; If more nibbles, loop
			mov		pc, r14			; Return


STRING	DCB "txt AEIOUaeiou TXT",&d,&a,0	; Original string
COUNTS	DCB	"Vowel Count: ",0				; Count string

		END
