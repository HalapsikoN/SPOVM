<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>AddToServer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="shortcut icon" href="https://image.flaticon.com/icons/png/512/93/93155.png">
    <style>
    body {
    background:  url(https://images.wallpaperscraft.ru/image/sobaka_schenok_chernyy_malysh_1174_1920x1080.jpg); /* Цвет фона и путь к файлу */
    }
    </style>
  </head>
  <body>
    <h1 style="text-align:center" class="text-white">ADD TO SERVER</h1>
    <br>
    <h3 style="text-align:center" class="text-white">Enter the header and information you want to link to this header to save</h3>
    <br><br><br>
    <form action="http://localhost/server/add" method="post">
      <div class="form-group">
        <label for="exampleInputEmail" class="text-white">Herader</label>
        <input class="form-control"  placeholder="Enter the header" name="header">
      </div>
      <div class="form-group">
        <label for="exampleFormControlTextarea" class="text-white">Information</label>
        <textarea class="form-control" rows="3" placeholder="Enter the infomation you want to save" name="inf"></textarea>
      </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a class="btn btn-warning" href="http://localhost/server" role="button">Back to menu</a>
      </form>
  </body>
</html>
