	;***************************
	; 	File: conversion.s
	;   Author: Alex Howard
	;	This program takes an IEEE754 defined number and converts it to TNS and vice versa.
	;	Date: March 2014
	;***************************

	; r0 = OUTPUT
	; r1 = CONVERTED NUMBER
	; r2 = SIGN mask
	; r3 = EXPONENT mask
	; r4 = MANTISSA mask
	; r5 = SIGN unpack
	; r6 = EXPONENT unpack
	; r7 = MANTISSA unpack
	; r8 = COMPARE
	; r9 = SAVE
	; r10 = ORIGINAL NUMBER
	; r11 = TRUE
	; r12 = FALSE


		AREA conversion, CODE, READONLY

SWI_WriteC	EQU &0
SWI_Exit	EQU &11

		ENTRY

		bl		PrintIEEE	; Print the IEEE number
		bl		PrintTNS	; Print the TNS number
		bl		IEEEtoTNS	; Convert the IEEE number to TNS
		bl		PrintConv	; Print the resultant TNS
		bl		CompareTNS	; Compare the resultant with the TNS number
		bl		TNStoIEEE	; Convert back to IEEE
		bl		PrintConv	; Print the resultant IEEE
		bl		CompareIEEE	; Compare the resultant IEEE to the IEEE number

		swi		SWI_Exit	; Exit

IEEEtoTNS
				; LOAD
			ldr		r1, IEEE 		; Load IEEE into r1
			ldr		r2, SIGN		; Load SIGN mask into r2
			ldr		r3, IEXP		; Load IEEE EXPONENT mask into r3
			ldr		r4, IMAN		; Load IEEE MANTISSA mask into r4
				; UNPACK
			and		r5, r1, r2		; Unpack sign bit, store to r5
			and		r6, r1, r3		; Unpack exponent bits, store to r6
			and		r7, r1, r4		; Unpack mantissa bits, store to r7
				; CONVERT EXPONENT
			mov		r6, r6, LSR #23	; Shift exponent left by 23 bits
			sub		r6, r6, #127	; Subtract 127 destroy bias
			add		r6, r6, #256	; Add 256 to create bias
				; CONVERT MANTISSA
			add		r7, r9, r7, LSL #8 ; Shift mantissa left 8 bits and destroy last bit
				; PACK
			mov		r1, #0			; Clear r1
			orr		r1, r5, r6		; OR the sign and exponent, store to r1
			orr		r1, r1, r7		; OR the resultant of the sign and exponent OR with the mantissa, store to r1
			mov		pc, lr			; Return


TNStoIEEE
				; LOAD
			ldr		r1, TNS			; Load TNS into r1
			ldr		r2, SIGN		; Load SIGN mask into r1
			ldr		r3, TEXP		; Load TNS EXPONENT mask into r3
			ldr		r4, TMAN		; Load TNS MANTISSA mask into r4
				; UNPACK
			and		r5, r1, r2		; Unpack sign bit, store to r5
			and		r6, r1, r3		; Unpack exponent bits, store to r6
			and		r7, r1, r4		; Unpack mantissa bits, store to r7
				; CONVERT EXPONENT
			sub		r6, r6, #129		; Subtract 129 to correct bias
			add		r6, r9, r6, LSL #23 ; Shift exponent left by 23 bits
				; CONVERT MANTISSA
			mov		r7, r7, LSR #8		; Shift mantissa right by 8 bits
				; PACK
			mov		r1, #0			; Clear r1
			orr		r1, r5, r6		; OR the sign and exponent, store to r1
			orr		r1, r1, r7		; OR the resultant of the sign and exponent OR with the mantissa, store to r1
			mov		pc, lr			; Return

PrintIEEE	ldr		r9, IEEE			; Load the IEEE number
			mov		r8, #8          	; Count of nibbles = 8
LOOPPI      mov		r0, r9, LSR #28 	; Get top nibble
            cmp		r0, #9          	; Hex number 0-9 or A-F?
            addgt   r0, r0, #"A"-10 	; ASCII A-F
            addle   r0, r0, #"0"    	; ASCII 0-9
            swi 	SWI_WriteC      	; Print character to console
            mov		r9, r9, LSL #4  	; Shift left one nibble
            subs    r8, r8, #1      	; Decrement nibble count
            bne     LOOPPI          	; If more nibbles loop again
            mov		r0, #10         	; Newline
            swi		SWI_WriteC      	; Write newline
            mov		pc, lr          	; Return

PrintTNS	ldr		r9, TNS				; Load the TNS number
			mov		r8, #8          	; Count of nibbles = 8
LOOPPT      mov		r0, r9, LSR #28 	; Get top nibble
            cmp		r0, #9          	; Hex number 0-9 or A-F?
            addgt   r0, r0, #"A"-10 	; ASCII A-F
            addle   r0, r0, #"0"    	; ASCII 0-9
            swi 	SWI_WriteC      	; Print character to console
            mov		r9, r9, LSL #4  	; Shift left one nibble
            subs    r8, r8, #1      	; Decrement nibble count
            bne     LOOPPT          	; If more nibbles loop again
            mov		r0, #10         	; Newline
            swi		SWI_WriteC      	; Write newline
            mov		pc, lr          	; Return


PrintConv   mov		r9, r1				; Preserve number
			mov		r8, #8          	; Count of nibbles = 8
LOOPPC      mov		r0, r9, LSR #28 	; Get top nibble
            cmp		r0, #9          	; Hex number 0-9 or A-F?
            addgt   r0, r0, #"A"-10 	; ASCII A-F
            addle   r0, r0, #"0"    	; ASCII 0-9
            swi 	SWI_WriteC      	; Print character to console
            mov		r9, r9, LSL #4  	; Shift left one nibble
            subs    r8, r8, #1      	; Decrement nibble count
            bne     LOOPPC          	; If more nibbles loop again
            mov		r0, #10         	; Newline
            swi		SWI_WriteC      	; Write newline
            mov		pc, lr          	; Return


CompareTNS
			ldr		r10, TNS		; Load the TNS number
			adr		r11, TRUE		; Load the T for true
			adr		r12, FALSE		; Load the F for false
			cmp		r1, r10			; Compare the resultant to the TNS number
			ldreq	r0, [r11]		; If it is the TNS put T in the Output Register (r0)
			ldrne	r0, [r12]		; If not equal, put F in the Output Register (r0)
			swi		SWI_WriteC		; Write the output
			mov		r0, #10			; Newline
			swi		SWI_WriteC		; Write newline
			mov		pc, lr			; Return

CompareIEEE
			ldr		r10, IEEE		; Load the IEEE number
			adr		r11, TRUE		; Load the T for true
			adr		r12, FALSE		; Load the F for false
			cmp		r1, r10			; Compare the resultant to the IEEE number
			ldreq	r0, [r11]		; If it is the IEEE put T in the Output Register (r0)
			ldrne	r0, [r12]		; If not equal, put F in the Output Register (r0)
			swi		SWI_WriteC		; Write the output
			mov		r0, #10			; Newline
			swi		SWI_WriteC		; Write newline
			mov		pc, lr			; Return


IEEE	DCD 	0x420b0000	; 34.75 in hexadecimal IEEE
TNS		DCD 	0x0b000105	; 34.75 in hexadecimal TNS
TEXP	DCD		0x000001ff	; TNS Exponent mask
TMAN	DCD		0x7ffffe00	; TNS Mantissa mask
IEXP	DCD		0x7f800000	; IEEE Exponent mask
IMAN	DCD		0x007fffff	; IEEE Mantissa mask
SIGN	DCD		0x80000000	; Sign bit mask
TRUE	DCB		"T"
FALSE	DCB		"F"

		END
