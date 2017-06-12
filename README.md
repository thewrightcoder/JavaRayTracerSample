<span style="font-size: 24pt"><strong>Ray Tracer in Java</strong></span>
<span style="font-size: 16pt">
<p>
Welcome to my Ray Tracer!  I've written this application with the goal of creating a sample of my Java code.
<br>
You can learn more about me from <a href="http://www.thewrightcoder.com">my website</a>.
</p>
</span>
<span style="font-size: 20pt"><strong><p>Why Java?</p></strong></span>
<span style="font-size: 16pt">
<p>
I wanted to learn a language and my research showed that a lot of companies use Java.  It seemed like a useful skill to have. 
</p>
</span>
<span style="font-size: 20pt"><strong><p>Why Ray Tracing?</p></strong></span>
<span style="font-size: 16pt">
<p>
Whenever I set out to learn a new language, I always start with the basics -- "Hello World!", followed by making a simple state machine.  From there, I want something more complex.
  Ray Tracing is something that is sufficiently complex that I will, at some point, be required to debug.  This means that it's a good example for learning more complex features of most languages.
</p>
</span>
<span style="font-size: 20pt"><strong><p>Additional Information</p></strong></span>
<span style="font-size: 16pt">
<p>
This Ray Tracer isn't meant to be competitive with professional applications, such as 3DS Max and Maya.  It's meant to be a sample of my coding skills.  As such, there are still features I'd like to add.
  Here's a list of a few features I want to add in the future:
<ul>
  <li>Textures</li>
  <li>Polygons</li>
  <li>Polygon Mesh (with .obj support)</li>
  <li>Tea Pot, Cube, and Cylinder parametric primitives</li>
  <li>Remove "Scene Outliner".  Originally I was going to have one tree object, but the results were undesirable.    I'd like to roll the "Scene Outliner" and the "Frame Outliner" into one object, though.</li>
  <li>Currently, if the user closes the render preview, the currently in progress render doesn't stop.  This means that those threads are still ticking away rendering, using CPU cycles.  They should end when the render preview is closed (or maybe add a cancel render button?)</li>
  <li>At some point, I'd like to shift the rendering work to the GPU instead of the CPU.</li>
</ul> 
</p>
</span>
<span style="font-size: 20pt"><strong><p>Sources</p></strong></span>
<span style="font-size: 16pt">
<p>
Over the course of this project, I used several external sources.
<br>
Original icons made by <a href="http://www.flaticon.com/authors/freepik">Freepik</a> from <a href="http://www.flaticon.com">FlatIcon</a>
<br>
I added things to the original art.
<br>
ImprovedFormattedTextField and associated classes came from <a href="http://stackoverflow.com/questions/1313390/is-there-any-way-to-accept-only-numeric-values-in-a-jtextfield">this</a> post on Stack Overflow. 
<br>
I also used this book, available from <a href="https://www.amazon.com/Realistic-Tracing-Second-Peter-Shirley/dp/1568811985">Amazon</a> :
<br>
<p style="margin-left: 40px">Shirley, Peter, and R. Keith Morley. Realistic Ray Tracing. Natick, MA: AK Peters, 2003. Print.</p>
</p>
</span>
