/** 
 * Testing name lookup in C on OSX Sierra
 *
 * compile:  cc -o name-test ./getaddrinfo.c
 *
 */
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <err.h>
#include <string.h>

int main(void) {
  char hostname[NI_MAXHOST];
  struct addrinfo *result;
  struct addrinfo *res;
  struct addrinfo hints;

  if (0 != gethostname(hostname, NI_MAXHOST)) {
    fprintf(stderr, "Failure to get hostname\n");
    exit(1);
  }

  fprintf(stdout, "Hostname: %s\n", hostname);


  memset(&hints, 0, sizeof(hints));
  hints.ai_family   = PF_UNSPEC;
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_flags    = AI_ADDRCONFIG | AI_CANONNAME | AI_V4MAPPED_CFG;

  int error;
  if ((error = getaddrinfo(hostname, "0", &hints, &result))) {
    fprintf(stdout, "Error on getaddrinfo(\"%s\", \"0\", &hits, &result)\n", hostname);
    //errx(1, "%s", gai_strerror(error));
    /*NOTREACHED*/
  }

  struct hostent * hostbyname = NULL;

  if ((hostbyname = gethostbyname(hostname)) == NULL) {
    herror("Error on gethostbyname");
    exit(1);
  }

  char host_buf[NI_MAXHOST], service_buf[NI_MAXSERV];
  char hostname2[NI_MAXHOST];

  for (res = result; res; res = res->ai_next) {

    struct sockaddr *sa = res->ai_addr;

    fprintf(stdout, "canonical_name=%s ", res->ai_canonname);
    if ((error = getnameinfo(sa, sa->sa_len, host_buf, sizeof(host_buf),
            service_buf, sizeof(service_buf), NI_NUMERICHOST | NI_NUMERICSERV))) {
      fprintf(stdout, "\n");
      errx(1, "getnameinfo numerical: %s", gai_strerror(error));
    }

    fprintf(stdout, "numerical_host=%s service=%s ",host_buf, service_buf);

    if ((error = getnameinfo(sa, sa->sa_len, hostname2, sizeof(hostname2), NULL, 0, NI_NAMEREQD))) {
      fprintf(stdout, "\n");
      errx(1, "getnameinfo named: %s", gai_strerror(error));
    }

    fprintf(stdout, "named_host=%s\n", hostname2);
  }
  freeaddrinfo(res);
}
