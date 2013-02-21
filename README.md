# Fall 12 CS 157 Project - Team 23

[Google Group](https://groups.google.com/forum/?fromgroups#!forum/cs157a_team23)<br />
[SVN repo](http://code.google.com/p/db-project-23/)

## How to Retrieve Data from Oralce - by Jerry

>Since the Result class has been changed to static, you don't need to create an object to call the methods in Result class. What you can do is simple, 
for example, Result.getAutoMaker(); will return you the String Array of all automakers

1. **Make sure you have start your Oracle Services: *OracleOraDb11g_home1TNSListener***

1.	**In DBOperation**<br />
	private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:<b>scott</b>";<br />
	private final String DB_USER = **"system"**;<br />
	private final String DB_PASS = **"tiger"**;<br /><br/>
	**Change the DB\_URL, DB\_USER, and DB\_PASS to specific value for your laptop.**
	
1. Here's the methods and javadoc for the methods you will use<br />
	<pre><code>
	   /**
		* Get an ArrayList of of AutoMaker
		* @return AutoMaker List
		*/
		<b><u>public static String[] getAutoMaker()</u></b><br />
		/**
		 * Get a list of model according to auto maker
		 * @param maker Auto Maker
		 * @return a list of model
		 */
		<u>public static String[] getModel(String maker)</u><br/ >
		/**
		 * Get a list of model according to auto maker
		 * @param maker Auto Maker
		 * @return a list of model
		 */
		<u>public static String[] getModel(String maker)</u><br />
		/**
		 * Get a list of year according to auto maker, model
		 * @param maker auto maker
		 * @param model auto model
		 * @return a list of year
		 */
		<u>public static String[] getYear(String maker, String model)</u><br />
		/**
		 * Get the Description table according to maker, model, and year
		 * @param maker Auto Maker
		 * @param model Auto Model
		 * @param year	make Year
		 * @return a String stores table with attributes: DESCRIPTION, LITRES, ENGINE_TYPE, CUBIC_INCHES, RLINK
		 * @throws SQLException
		 */
		public static String getAllDesc(String maker, String model, String year)
	</code></pre>
	<br />
	_The SQLTester.java is currently a sample test for the getAllDesc, it shows what will be output for this query method_
### P.S.
This document use markdown syntax, basically it's a simpler way to write html codes. If you want to add any thing to this page, this website can help you about the syntax of mark down: 
[http://daringfireball.net/projects/markdown/syntax](http://daringfireball.net/projects/markdown/syntax)
