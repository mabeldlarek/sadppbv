function NavigationLogged() {
    return (
      <nav className="navbar navbar-expand-lg custom-navbar">
      <div className="container-fluid">
      <a className="navbar-brand" href="#">
        <img src={Logo} alt="" width="200" height="100" class="d-inline-block align-text-top"/>
      </a>
          <a className="navbar-brand" href="#"></a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
            <div className="navbar-nav">
              <a className="nav-link custom-link " aria-current="page" href="#">Logout</a>
            </div>
          </div>
      </div>
      </nav>
    );
  }