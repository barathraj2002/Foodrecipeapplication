<!DOCTYPE html>
<html lang="en">
<head>
  <title>Add Recipe</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default rounded borders and increase the bottom margin */ 
    .navbar {
      margin-bottom: 50px;
      border-radius: 0;
    }
    
    /* Remove the jumbotron's default bottom margin */ 
     .jumbotron {
      margin-bottom: 0;
    }
   
    /* Add a gray background color and some padding to the footer */
    footer {
      background-color: #f2f2f2;
      padding: 25px;
    }
    .form-container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
      background-color: #f9f9f9;
    }
  </style>
</head>
<body>

<div class="jumbotron">
  <div class="container text-center">
    <h2>Food + Recipes!</h2>
    <h3>Foodipes!</h3>      
    <p>Mission, Vision & Values</p>
  </div>
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Logo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li ><a href="home.jsp">Home</a></li>
        <li class="active"><a href="addRecipe.jsp">Add Recipe</a></li>
        <li><a href="#">Connect to Chef</a></li>
        <li><a href="#">Buy Food</a></li>
        <li><a href="#">Support</a></li>
      </ul>
      
    </div>
  </div>
</nav>

<div class="container form-container">    
  <h2 class="text-center">Add Recipe</h2>
  <form action="AddRecipeServlet" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="recipeName">Recipe Name:</label>
      <input type="text" class="form-control" id="recipeName" name="recipeName" required>
    </div>
    <div class="form-group">
      <label for="recipeContent">Recipe Content:</label>
      <textarea class="form-control" id="recipeContent" name="recipeContent" rows="8" required></textarea>
    </div>
    <div class="form-group">
      <label for="recipeImage">Recipe Image:</label>
      <input type="file" class="form-control" id="recipeImage" name="recipeImage" required>
    </div>
    <div class="text-center">
      <button type="submit" class="btn btn-primary">Add Recipe</button>
    </div>
  </form>
</div>

</body>
</html>
