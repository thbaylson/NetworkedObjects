### Author: Tyler Baylson
### Date: 10/28/20
### Class: CS 465 : Computer Networks
### Professor: Dr. Scott Barlowe

# Description:
    A client/server application implemented using TCP and UDP. The following
    operational instructions assume you are in the project2 directory. Flags
    are RFC-20mg compliant.

# Notes on card types:
    In the RFC-20mg description, there was no distinction between basic lands
    and non-basic lands. I also couldn't find specification within the project
    instructions. Therefore, the flag -L will display all land cards not just
    the basic Forest, Island, Mountain, Plains, and Swamp.

# How to compile:
    javac common/*.java client/*.java server/*.java

# How to start the server:

    Default TCP settings:
        java server.MagicServerDriver tcp

    TCP with specific port number:
        java server.MagicServerDriver tcp port#

    Default UDP settings:
        java server.MagicServerDriver udp

    UDP with specific port number:
        java server.MagicServerDriver udp port#

# How to start the client:

    Default TCP settings:
        java client.MagicClientDriver tcp localhost

    TCP with specific port number:
        java client.MagicClientDriver tcp localhost port#

    TCP with specific flags:
        java client.MagicClientDriver tcp localhost -flags

    TCP with specific port number and flags:
        java client.MagicClientDriver tcp localhost port# -flags

    Default UDP settings:
        java client.MagicClientDriver udp localhost

    UDP with specific port number:
        java client.MagicClientDriver udp localhost port#

    UDP with specific flags:
        java client.MagicClientDriver udp localhost -flags

    UDP with specific port number and flags:
        java client.MagicClientDriver udp localhost port# -flags
