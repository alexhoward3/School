use Random;

var number: real;
var rs = new RandomStream(SeedGenerator.currentTime, parSafe=false);
for i in 0..9 {
    number = (10 * rs.getNext()) : int;
    writeln(i + ": = " + number);
}