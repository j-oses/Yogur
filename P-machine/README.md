# P-machine emulator

This package provides a complete interpreter of the original N. Wirth's P-machine

- It expects a text file having a sequence of P-instructions ended by a ';'
- It sets up a CODE segment and a STORE array, and executes the code
- As a result, the final machine state is printed
- A set of exceptions are checked. If any of them arises, the machine reports the error, and stops

## Ownership

The files in this package have the following license and copyright:
- Copyright   :  (c) Ricardo Peña, Jun. 2014, Universidad Complutense de Madrid
- License     :  LGPL
- Maintainer  :  ricardo@sip.ucm.es