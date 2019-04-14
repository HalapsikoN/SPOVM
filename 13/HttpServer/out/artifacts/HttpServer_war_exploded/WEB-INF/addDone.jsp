<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>AddToServer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="shortcut icon" href="https://memepedia.ru/wp-content/uploads/2019/04/124210-full.png">
  </head>
  <body>
    <h1 style="text-align:center">ADD TO SERVER</h1>
    <br>
    <h3 style="text-align:center">Enter the header and information you want to link to this header to save</h3>
    <br><br><br>
    <form action="http://localhost/server/add" method="post">
      <div class="form-group">
        <label for="exampleInputEmail">Herader</label>
        <input class="form-control"  placeholder="Enter the header" name="header">
      </div>
      <div class="form-group">
        <label for="exampleFormControlTextarea">Information</label>
        <textarea class="form-control" rows="3" placeholder="Enter the infomation you want to save" name="inf"></textarea>
      </div>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
      <h3><span class="badge badge-pill badge-success">Successfuly done</span><h3>
  </body>
</html>
